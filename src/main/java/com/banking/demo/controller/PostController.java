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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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

import com.banking.demo.lib.Pager;
import com.banking.demo.models.Category;
import com.banking.demo.models.Post;
import com.banking.demo.models.Tag;
import com.banking.demo.repositories.CategoryRepository;
import com.banking.demo.repositories.PostRepository;
import com.banking.demo.repositories.TagRepository;
import com.banking.demo.service.MessageService;
import com.banking.demo.service.MySecurityService;
import com.banking.demo.service.PostService;

@Controller
@RequestMapping(value = "admin/posts")
@Transactional
public class PostController {

	private static final int BUTTONS_TO_SHOW = 5;
	private static final int INITIAL_PAGE = 0;
	private static final int INITIAL_PAGE_SIZE = 2;
	private static final int[] PAGE_SIZES = { 5, 10, 20 };

	private PostRepository postsRepository;
	private TagRepository tagsRepository;
	private CategoryRepository categoryRepository;
	private MessageService messageService;
	@Autowired
	public PostController(PostRepository postsRepository, TagRepository tagsRepository,
			CategoryRepository categoryRepository, MessageService messageService) {
		this.postsRepository = postsRepository;
		this.tagsRepository = tagsRepository;
		this.categoryRepository = categoryRepository;
		this.messageService = messageService;
	}

	
	@PreAuthorize("hasRole('ADMIN') and hasPermission('hasAccess','Tester') ")
	@GetMapping({ "", "/" })
	public String index(Model model, @RequestParam("pageSize") Optional<Integer> pageSize,
			@RequestParam("page") Optional<Integer> page,
			@RequestParam(name = "search", defaultValue = "") String search) {

		String likeNameOrTitile = "";
		
		UserDetails user = MySecurityService.getUserDetail();
		
		System.out.println(user.getAuthorities().toString());
		
		if (search.trim() != "") {
			likeNameOrTitile = "%" + search + "%";
		}

		int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
		int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

		Page<Post> posts = postsRepository.findByNameIsLikeOrTitleIsLike(likeNameOrTitile, likeNameOrTitile,
				new PageRequest(evalPage, evalPageSize));

		Pager pager = new Pager(posts.getTotalPages(), posts.getNumber(), BUTTONS_TO_SHOW);

		Integer pageNumber = null;

		if (page.isPresent()) {
			pageNumber = page.get();
		}

		model.addAttribute("posts", posts);
		model.addAttribute("selectedPageSize", evalPageSize);
		model.addAttribute("active", pageNumber);
		model.addAttribute("pageSizes", PAGE_SIZES);
		model.addAttribute("pager", pager);
		model.addAttribute("search", search);
		return "admin/posts/index";
	}

	@GetMapping({ "/new", "/create" })
	public String create(Model model) {

		model.addAttribute("post", new Post());
		model.addAttribute("categories", categoryRepository.findAll());
		model.addAttribute("tags", tagsRepository.findAll());
		return "admin/posts/new";
	}

	@PostMapping("/create")
	public String store(@Valid Post postSubmitted, Errors validation,
			@RequestParam(name = "tags", defaultValue = "") List<Tag> Tags, Model model,RedirectAttributes redirectAttributes) {

		if (validation.hasErrors()) {
			model.addAttribute("errors", validation);
			System.out.println(validation.getAllErrors());
			model.addAttribute("post", postSubmitted);
			model.addAttribute("categories", categoryRepository.findAll());
			model.addAttribute("tags", tagsRepository.findAll());
			return "admin/posts/new";
		}
		postSubmitted.setTags(Tags);
		postsRepository.save(postSubmitted);
		redirectAttributes.addAttribute("success","Alert Success");
		return "redirect:/admin/posts";
	}

	@GetMapping(value = "/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Model model) {

		Post post = postsRepository.findOne(id);
		model.addAttribute("post", post);
		model.addAttribute("categories", categoryRepository.findAll());
		model.addAttribute("tags", tagsRepository.findAll());

		return "admin/posts/edit";
	}

	@PostMapping("/update")
	public String update(@Valid Post postSubmitted, Errors validation,
			@RequestParam(name = "tags", defaultValue = "") List<Tag> Tags, Model model, RedirectAttributes redirectAttributes) {

		if (validation.hasErrors()) {
			model.addAttribute("errors", validation);
			System.out.println(validation.getAllErrors());
			model.addAttribute("post", postSubmitted);
			model.addAttribute("categories", categoryRepository.findAll());
			model.addAttribute("tags", tagsRepository.findAll());
			return "admin/posts/edit";
		}
		Post postUpdate = postsRepository.findOne(postSubmitted.getId());
		postUpdate.setTags(Tags);
		postUpdate.setName(postSubmitted.getName());
		postUpdate.setTitle(postSubmitted.getTitle());
		postUpdate.setCategory(postSubmitted.getCategory());
		postsRepository.save(postUpdate);
		messageService.addMessage("Updated Success","success",redirectAttributes);
		redirectAttributes.addFlashAttribute("messages",messageService.getMessage());
		return "redirect:/admin/posts";
	}

	@GetMapping("delete/{id}")
	public String destroy(@PathVariable("id") Integer id) {
		postsRepository.delete(id);
		return "redirect:/admin/posts";
	}

	@PostMapping("/search")
	@ResponseBody
	public String search(@RequestParam(name = "search") String search) {

		search = "%" + search + "%";

		return "";
	}

}
