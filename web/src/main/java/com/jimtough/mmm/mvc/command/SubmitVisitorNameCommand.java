package com.jimtough.mmm.mvc.command;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.jimtough.mmm.data.jpa.entity.SiteVisitor;
import lombok.Getter;
import lombok.Setter;

@Getter
public class SubmitVisitorNameCommand {

	private final int nicknameMinLength = SiteVisitor.NICKNAME_MIN_LENGTH;
	private final int nicknameMaxLength = SiteVisitor.NICKNAME_MAX_LENGTH;

	@Setter
	@Pattern(regexp = "[A-Za-z0-9]+")
	@NotBlank
	@Size(min = SiteVisitor.NICKNAME_MIN_LENGTH, max = SiteVisitor.NICKNAME_MAX_LENGTH)
	private String nickname;

}
