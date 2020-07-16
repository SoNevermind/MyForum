package com.forum.forum.controllers;

import com.forum.forum.models.Post;
import com.forum.forum.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class ForumController {
    @Autowired
    private PostRepository postRepository; //переменная ссылающаяся на репозиторий

    @GetMapping("/forum")
    public String forumMain(Model model){
        Iterable<Post> posts = postRepository.findAll(); //массив данных, в котором находятся все значения БД
        model.addAttribute("posts", posts); //передача записей в шаблон
        return "forum_main";
    }

    @GetMapping("/forum/add")
    public String forumAdd(Model model){
        return "forum_add";
    }

    @PostMapping("/forum/add")
    public String forumPostAdd(@RequestParam String name, @RequestParam String title, @RequestParam String answer, Model model){
        Post post = new Post(name, title, answer);
        postRepository.save(post); //сохранение в БД
        return "redirect:/forum";
    }

    @GetMapping("/forum/{id}")
    public String forumDetails(@PathVariable(value = "id") long id, Model model){
        if(!postRepository.existsById(id)){
            return "redirect:/forum";
        }

        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res :: add);
        model.addAttribute("post", res);
        return "forum_details";
    }

    @GetMapping("/forum/{id}/edit")
    public String forumEdit(@PathVariable(value = "id") long id, Model model){
        if(!postRepository.existsById(id)){
            return "redirect:/forum";
        }

        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res :: add);
        model.addAttribute("post", res);
        return "forum_edit";
    }

    @PostMapping("/forum/{id}/edit")
    public String forumPostUpdate(@PathVariable(value = "id") long id, @RequestParam String name, @RequestParam String title, @RequestParam String answer, Model model){
        Post post = postRepository.findById(id).orElseThrow();
        post.setName(name);
        post.setTitle(title);
        post.setAnswer(answer);
        postRepository.save(post);

        return "redirect:/forum";
    }

    @PostMapping("/forum/{id}/remove")
    public String forumPostDelete(@PathVariable(value = "id") long id, Model model){
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);

        return "redirect:/forum";
    }
}
