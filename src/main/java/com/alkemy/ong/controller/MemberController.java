package com.alkemy.ong.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alkemy.ong.dto.MemberDto;
import com.alkemy.ong.service.IMemberService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import com.alkemy.ong.util.MessageHandler;

@RestController
@RequestMapping(MemberController.MEMBERS)
public class MemberController {

	protected static final String MEMBERS = "/members";

	@Autowired
	private IMemberService memberService;

	@Autowired
	private MessageHandler messageHandler;

	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody MemberDto memberDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(memberService.save(memberDto));
	}

	@GetMapping
	public ResponseEntity<?> findAll() {
		List<?> result = memberService.findAll();
		return ResponseEntity.ok().body(!result.isEmpty() ?
				result : messageHandler.membersNotFound);
	}

}