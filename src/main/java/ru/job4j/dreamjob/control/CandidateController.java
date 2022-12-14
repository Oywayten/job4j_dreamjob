package ru.job4j.dreamjob.control;

import net.jcip.annotations.ThreadSafe;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.service.CandidateService;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

import static ru.job4j.dreamjob.util.Util.setUser;

/**
 * Created by Oywayten on 05.10.2022.
 * Класс-контроллер для кандидатов.
 */
@Controller
@ThreadSafe
public class CandidateController {
    private final CandidateService candidateService;

    public CandidateController(CandidateService service) {
        this.candidateService = service;
    }


    @GetMapping("/candidates")
    public String candidates(Model model, HttpSession session) {
        model.addAttribute("candidates", candidateService.findAll());
        setUser(session, model);
        return "candidates";
    }

    @GetMapping("/formAddCandidate")
    public String addCandidate(Model model, HttpSession session) {
        model.addAttribute("candidate", new Candidate(0, "Заполните поле", "Заполните поле", LocalDateTime.now()));
        setUser(session, model);
        return "addCandidate";
    }

    @PostMapping("/createCandidate")
    public String createCandidate(@ModelAttribute Candidate candidate, @RequestParam("file") MultipartFile file)
            throws Exception {
        candidate.setPhoto(file.getBytes());
        candidateService.add(candidate);
        return "redirect:/candidates";
    }

    @GetMapping("/formUpdateCandidate/{candidateId}")
    public String formUpdateCandidate(Model model, @PathVariable("candidateId") int id, HttpSession session) {
        model.addAttribute("candidate", candidateService.findById(id));
        setUser(session, model);
        return "updateCandidate";
    }

    @PostMapping("/updateCandidate")
    public String updateCandidate(@ModelAttribute Candidate candidate, @RequestParam("file") MultipartFile file)
            throws Exception {
        candidate.setPhoto(file.getBytes());
        candidateService.update(candidate);
        return "redirect:/candidates";
    }

    @GetMapping("/photoCandidate/{candidateId}")
    public ResponseEntity<Resource> download(@PathVariable("candidateId") Integer candidateId) {
        Candidate candidate = candidateService.findById(candidateId);
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(candidate.getPhoto().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(candidate.getPhoto()));
    }
}