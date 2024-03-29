package com.jimtough.mmm.mvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.jimtough.mmm.data.jpa.entity.SiteVisit;
import com.jimtough.mmm.data.jpa.entity.SiteVisitor;
import com.jimtough.mmm.data.repository.SiteVisitRepository;
import com.jimtough.mmm.data.repository.SiteVisitorRepository;
import com.jimtough.mmm.hello.HelloFactory;
import com.jimtough.mmm.mvc.command.SubmitVisitorNameCommand;
import com.jimtough.mmm.mvc.session.SiteVisitorSessionStuff;
import com.jimtough.mmm.world.WorldFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Slf4j
@RequiredArgsConstructor
@Controller
@SessionAttributes({"siteVisitorSessionStuff"})
public class IndexController {

	private static final String MODELATTR_HELLO_STRING = "helloString";
	private static final String MODELATTR_WORLD_STRING = "worldString";
	private static final String MODELATTR_CURRENT_TIME = "currentTime";
	private static final String MODELATTR_VISITOR_NICKNAME = "visitorNickname";

	private final HelloFactory helloFactory;
	private final WorldFactory worldFactory;
	private final SiteVisitorRepository siteVisitorRepository;
	private final SiteVisitRepository siteVisitRepository;

	// Support sometimes used 'http://myhostname/index' path to home page.
	@GetMapping("index")
	public String redirectToIndexView() {
		return "redirect:/";
	}

	private void populateModelWithSiteVisitorDetails(Model model, SiteVisitorSessionStuff siteVisitorSessionStuff) {
		model.addAttribute(MODELATTR_VISITOR_NICKNAME, siteVisitorSessionStuff.getNickname().get());
		if (siteVisitorSessionStuff.getSiteVisitorId().isPresent()) {
			final Long siteVisitorId = siteVisitorSessionStuff.getSiteVisitorId().get();
			final SiteVisitor siteVisitor = siteVisitorRepository
					.findById(siteVisitorId)
					.orElseThrow(()->new IllegalArgumentException("Invalid site visitor id: " + siteVisitorId));
			List<SiteVisit> svList = siteVisitRepository.findFirst10BySiteVisitorOrderByVisitTimestampDesc(siteVisitor);
			log.debug("Found {} visits for visitor '{}' (retrieval capped at max of 10 visits)",
					svList.size(), siteVisitor.getUppercaseNickname());
			model.addAttribute("recentVisits", svList);
		}
	}

	// Support typical home page path variants
	@GetMapping({"","/"})
	public String getIndexView(Model model, SiteVisitorSessionStuff siteVisitorSessionStuff) {
		Objects.requireNonNull(siteVisitorSessionStuff);
		model.addAttribute(MODELATTR_HELLO_STRING, helloFactory.getHello());
		if (siteVisitorSessionStuff.getNickname().isPresent()) {
			populateModelWithSiteVisitorDetails(model, siteVisitorSessionStuff);
		} else {
			model.addAttribute(MODELATTR_WORLD_STRING, worldFactory.getWorld());
			model.addAttribute(MODELATTR_CURRENT_TIME, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
			model.addAttribute("submitVisitorNameCommand", new SubmitVisitorNameCommand());
		}
		return "index";
	}

	void handleSubmitVisitorName(
			SiteVisitorSessionStuff siteVisitorSessionStuff,
			String validNickname,
			HttpServletRequest httpServletRequest) {
		// Save the raw (but validated) nickname in the session object
		siteVisitorSessionStuff.setNickname(validNickname);
		// Retrieve or create a new SiteVisitor entity
		Optional<SiteVisitor> siteVisitorOptional =
				siteVisitorRepository.findOneByUppercaseNickname(validNickname.toUpperCase());
		final SiteVisitor siteVisitorEntity;
		if (siteVisitorOptional.isPresent()) {
			log.debug("Found matching entity in database for nickname '{}'", validNickname);
			siteVisitorEntity = siteVisitorOptional.get();
		} else {
			log.debug("Creating new entity for nickname '{}'", validNickname);
			siteVisitorEntity = siteVisitorRepository.save(SiteVisitor.builder()
					.nickname(validNickname)
                    .uppercaseNickname(validNickname.toUpperCase())
                    .build());
		}
		// Save the SiteVisitor entity id in the session object
		siteVisitorSessionStuff.setSiteVisitorId(siteVisitorEntity.getId());
		// NOTE: This is unlikely to get an accurate remote address when app is behind a load balancer.
		//       Need configure the load balancer (or whatever forwarded the request) to supply the source
		//       IP address in a request header value.
		final String remoteHostOrIpAddress = httpServletRequest.getRemoteHost();
		// Create a new SiteVisit entity for this visit
		siteVisitRepository.save(SiteVisit.builder().siteVisitor(siteVisitorEntity).remoteAddress(remoteHostOrIpAddress).build());
	}

	@PostMapping("/submit-visitor-name")
	public String submitVisitorName(
			Model model,
			SiteVisitorSessionStuff siteVisitorSessionStuff,
			@Valid SubmitVisitorNameCommand command,
			BindingResult bindingResult,
			HttpServletRequest httpServletRequest) {
		Objects.requireNonNull(model);
		Objects.requireNonNull(siteVisitorSessionStuff);
		Objects.requireNonNull(command);
		Objects.requireNonNull(bindingResult);
		Objects.requireNonNull(httpServletRequest);
		if (bindingResult.hasErrors()) {
			log.info("submitVisitorName() | {} has errors", BindingResult.class.getSimpleName());
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				log.info("submitVisitorName() | --> [{}] | [{}]", fieldError.getField(), fieldError.getDefaultMessage());
			}
			// Put the initial model attribute values back when validation fails
			model.addAttribute(MODELATTR_HELLO_STRING, helloFactory.getHello());
			model.addAttribute(MODELATTR_WORLD_STRING, worldFactory.getWorld());
			return "index";
		}
		handleSubmitVisitorName(siteVisitorSessionStuff, command.getNickname(), httpServletRequest);
		return "redirect:/";
	}

}
