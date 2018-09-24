package com.banking.demo.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.banking.demo.anotation.AuthorizeValidator;
import com.banking.demo.lib.Pager;
import com.banking.demo.models.Category;
import com.banking.demo.models.Post;
import com.banking.demo.models.Tag;
import com.banking.demo.repositories.CategoryRepository;
import com.banking.demo.repositories.PostRepository;
import com.banking.demo.repositories.TagRepository;
import com.banking.demo.service.MessageService;

@Controller
@RequestMapping(value = "admin/tags")
public class TagController {
	

	private static final int BUTTONS_TO_SHOW = 5;
	private static final int INITIAL_PAGE = 0;
	private static final int INITIAL_PAGE_SIZE = 2;
	private static final int[] PAGE_SIZES = { 5, 10, 20 };

	private TagRepository tagsRepository;
	private MessageService messageService;

	@Autowired
	public TagController(TagRepository tagsRepository, MessageService messageService) {
		this.tagsRepository = tagsRepository;
		this.messageService = messageService;
	}

	@GetMapping({ "", "/" })
	@AuthorizeValidator({"Admin","Member"})
	public String index(Model model, @RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page,
			@RequestParam(name = "search", defaultValue = "") String search) {

		String likeNameOrTitile = "";

		if (search.trim() != "") {
			likeNameOrTitile = "%" + search + "%";
		}

		int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
		int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

		Page<Tag> tags = tagsRepository.findAll(new PageRequest(evalPage, evalPageSize));

		Pager pager = new Pager(tags.getTotalPages(), tags.getNumber(), BUTTONS_TO_SHOW);

		Integer pageNumber = null;

		if (page.isPresent()) {
			pageNumber = page.get();
		}

		model.addAttribute("tags", tags);
		model.addAttribute("selectedPageSize", evalPageSize);
		model.addAttribute("active", pageNumber);
		model.addAttribute("pageSizes", PAGE_SIZES);
		model.addAttribute("pager", pager);
		model.addAttribute("search", search);
		return "admin/tags/index";
	}

	@GetMapping({ "/new", "/create" })
	public String create(Model model) {
		model.addAttribute("tag", new Tag());
		return "admin/tags/new";
	}

	@PostMapping("/create")
	public String store(@Valid Tag tagsubmitted, Errors validation, Model model,
			RedirectAttributes redirectAttributes) {

		if (validation.hasErrors()) {
			model.addAttribute("errors", validation);
			System.out.println(validation.getAllErrors());
			model.addAttribute("tag", tagsubmitted);
			return "admin/tags/new";
		}
		tagsRepository.save(tagsubmitted);
		messageService.addMessage("Success", "success", redirectAttributes);
		return "redirect:/admin/tags";
	}

	@GetMapping(value = "/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {

		Tag tag = tagsRepository.findOne(id);
		model.addAttribute("tag", tag);

		return "admin/tags/edit";
	}

	@PostMapping("/update")
	public String update(@Valid Tag tagsubmitted, Errors validation, Model model,
			RedirectAttributes redirectAttributes) {

		if (validation.hasErrors()) {
			model.addAttribute("errors", validation);
			System.out.println(validation.getAllErrors());
			model.addAttribute("tag", tagsubmitted);
			return "admin/tags/edit";
		}
		Tag tag = tagsRepository.findOne(tagsubmitted.getId());
		tag.setName(tagsubmitted.getName());
		tagsRepository.save(tag);
		
		messageService.addMessage("Success", "success", redirectAttributes);
		redirectAttributes.addFlashAttribute("messages", messageService.getMessage());
		return "redirect:/admin/tags";
	}

	@GetMapping("delete/{id}")
	public String destroy(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		tagsRepository.delete(id);
		messageService.addMessage("Success", "danger", redirectAttributes);
		redirectAttributes.addFlashAttribute("messages", messageService.getMessage());
		return "redirect:/admin/tags";
	}

}
