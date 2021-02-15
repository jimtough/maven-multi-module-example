package com.jimtough.mmm.mvc.session;

import static org.springframework.web.context.WebApplicationContext.SCOPE_SESSION;

import java.util.Optional;

import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Setter
@Component
@Scope(scopeName = SCOPE_SESSION)
public class SiteVisitorSessionStuff {

	private String nickname;
	private Long siteVisitorId;

	public Optional<String> getNickname() {
		return Optional.ofNullable(nickname);
	}

	public Optional<Long> getSiteVisitorId() {
		return Optional.ofNullable(siteVisitorId);
	}

}
