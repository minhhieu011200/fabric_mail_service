package com.example.mail_service.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class EmailLog extends BaseEntities {
	@NotNull
	private String toMail;
	private String attachment;

	// danh cho file html, jasper
	@ManyToOne
	@JoinColumn(name = "template_id", referencedColumnName = "id")
	private TemplateEmail templateEmail;
	private String dataTemplate;

	@Enumerated(EnumType.STRING)
	private EnumStatusMail status;
}
