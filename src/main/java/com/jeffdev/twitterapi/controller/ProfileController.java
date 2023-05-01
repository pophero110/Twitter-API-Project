package com.jeffdev.twitterapi.controller;

import com.jeffdev.twitterapi.model.Profile;
import com.jeffdev.twitterapi.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/profiles")
public class ProfileController {

    private ProfileService profileService;

    @Autowired
    public void setProfileService(ProfileService profileService) {
        this.profileService = profileService;
    }

    /**
     * create a profile in database and return it
     *
     * @param profileObject the profile object that needs to be created
     * @return created profile
     */
    @PostMapping("")
    public Profile createProfile(@Valid @RequestBody Profile profileObject) {
        return profileService.createProfile(profileObject);
    }

    /**
     * update a profile in database and return it
     *
     * @param profileObject the object that contains attributes that need be updated
     * @return updated profile
     */
    @PutMapping("")
    public Profile updateProfile(@Valid @RequestBody Profile profileObject) {
        return profileService.updateProfile(profileObject);
    }

    /**
     * Get a specific user's profile by user id
     * @param userId the id of the user that profile belongs to
     * @return Profile
     */
    @GetMapping("/users/{userId}")
    public Profile getProfile(@PathVariable Long userId) {
        return profileService.getProfileByUserId(userId);
    }
}
