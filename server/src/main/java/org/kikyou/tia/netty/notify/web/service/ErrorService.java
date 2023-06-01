package org.kikyou.tia.netty.notify.web.service;


import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.IOException;


@Service
public class ErrorService
{


    public String getError(final Model model) throws IOException
    {

        model.addAttribute("theme", "light");
        return "error/404";
    }
}