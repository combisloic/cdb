package com.excilys.webapp.controller;

import static com.excilys.core.User.ROLE_MANAGER;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.excilys.binding.dto.ComputerDTO;
import com.excilys.binding.mapper.ComputerDTOMapper;
import com.excilys.core.Feedback;
import com.excilys.service.service.CompanyService;
import com.excilys.service.service.ComputerService;
import com.excilys.webapp.validator.ComputerDTOValidator;

@Controller
@RequestMapping("/computers/add")
public class AddComputerController {

    /**
     * companyService CompanyService.
     */
    private CompanyService companyService;

    /**
     * computerService ComputerService.
     */
    private ComputerService computerService;

    private ComputerDTOMapper mapper;

    private ComputerDTOValidator validator;

    private MessageSource source;

    /**
     * logger Logger.
     */
    private Logger logger = LoggerFactory.getLogger(AddComputerController.class);

    /**
     * Constructor.
     *
     * @param companySer  CompanyService
     * @param computerSer ComputerService
     * @param dtoMapper   ComputerDTOMapper
     */
    public AddComputerController(CompanyService companySer, ComputerService computerSer, ComputerDTOMapper dtoMapper,
            ComputerDTOValidator computerValidator, MessageSource messageSource) {
        companyService = companySer;
        computerService = computerSer;
        mapper = dtoMapper;
        validator = computerValidator;
        source = messageSource;
    }

    /**
     * Init the computerDTOValidator.
     *
     * @param binder WebDataBinder
     */
    @InitBinder("computerDTO")
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    /**
     * Show the add computer page.
     *
     * @param feedback Optional<String>
     * @param message  Optional<String>
     * @param map      Model
     * @return String
     */
    @GetMapping
    @Secured(ROLE_MANAGER)
    protected String show(@RequestParam Optional<String> feedback, @RequestParam Optional<String> message, Model map) {
        map.addAttribute("feedback", new Feedback(feedback.orElse(""), message.orElse("")));
        map.addAttribute("companies", companyService.list(0, 0, "", "", false));
        return "add-computer";
    }

    /**
     * Init the model attribute computerDTO.
     *
     * @return ComputerDTO
     */
    @ModelAttribute
    public ComputerDTO computerDTO() {
        return new ComputerDTO();
    }

    /**
     * Endpoint to create a new computer.
     *
     * @param computerDTO ComputerDTO
     * @param result      BindingResult
     * @return RedirectView
     */
    @PostMapping
    @Secured(ROLE_MANAGER)
    protected RedirectView create(@Validated @ModelAttribute("computerDTO") ComputerDTO computerDTO,
            BindingResult result) {
        String status = "danger";
        String message = "";

        if (result.hasErrors()) {
            message = result.getAllErrors().get(0).getCode();

        } else if (computerService.create(mapper.toComputer(computerDTO))) {
            status = "success";
            message = source.getMessage(ComputerService.ADD_COMPUTER_SUCCESS, null, LocaleContextHolder.getLocale());
            logger.info("Computer created : " + computerDTO);

        } else {
            message = source.getMessage(ComputerService.ADD_COMPUTER_FAILURE, null, LocaleContextHolder.getLocale());
        }

        return new RedirectView("/computers/add?feedback=" + status + "&message=" + message);
    }
}
