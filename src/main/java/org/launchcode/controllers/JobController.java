package org.launchcode.controllers;

import org.launchcode.models.Job;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view
        Job job = jobData.findById(id);
        model.addAttribute("job", job);

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        JobForm jobForm = new JobForm();
        model.addAttribute(new Job());
        model.addAttribute("employers", jobForm.getEmployers());
        model.addAttribute("locations", jobForm.getLocations());
        model.addAttribute("positionTypes", jobForm.getPositionTypes());
        model.addAttribute("coreCompetencies", jobForm.getCoreCompetencies());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid Job job, Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.

        if (errors.hasErrors()) {
            JobForm jobForm = new JobForm();
            model.addAttribute("employers", jobForm.getEmployers());
            model.addAttribute("locations", jobForm.getLocations());
            model.addAttribute("positionTypes", jobForm.getPositionTypes());
            model.addAttribute("coreCompetencies", jobForm.getCoreCompetencies());
            return "new-job";

        }else{
            jobData.add(job);
            int jobId = job.getId();

            return "redirect:/job?id=" + jobId;
        }
        //return "new-job";

    }
}
