package com.jeffdev.twitterapi.service;

import com.jeffdev.twitterapi.exception.InformationExistException;
import com.jeffdev.twitterapi.exception.InformationInvalidException;
import com.jeffdev.twitterapi.exception.InformationNotFoundException;
import com.jeffdev.twitterapi.model.Profile;
import com.jeffdev.twitterapi.model.User;
import com.jeffdev.twitterapi.repository.ProfileRepository;
import com.jeffdev.twitterapi.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * Service class that handles profile-related operations.
 */
@Service
public class ProfileService {
    private ProfileRepository profileRepository;

    @Autowired
    public void setProfileRepository(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public static User getCurrentLoggedInUser() {
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return myUserDetails.getUser();
    }

    /**
     * Create a user's profile with given Profile object
     *
     * @param profileObject the profile object that need to created
     * @return created profile
     * @throws InformationExistException If the user has a profile already
     * @throws InformationExistException If the name in the given Profile object is blank
     */
    public Profile createProfile(Profile profileObject) {
        if (profileRepository.existsByUserId(getCurrentLoggedInUser().getId())) {
            throw new InformationExistException("You can only create one profile");
        } else {
            if (profileObject.getName().isBlank()) {
                throw new InformationInvalidException("Name can not be empty or only contains space character");
            }
            profileObject.setUser(getCurrentLoggedInUser());
            return profileRepository.save(profileObject);
        }
    }

    /**
     * Updates the current user's profile with the given Profile object.
     * If the current user doesn't have a profile, an InformationNotFoundException will be thrown.
     * If the name in the provided Profile object is blank, an InformationInvalidException will be thrown.
     *
     * @param profileObject The Profile object containing the updated profile information.
     * @return The updated Profile object.
     * @throws InformationNotFoundException If the current user doesn't have a profile.
     * @throws InformationInvalidException  If the name in the provided Profile object is blank.
     */
    public Profile updateProfile(Profile profileObject) {
        Optional<Profile> profile = profileRepository.findByUserId(getCurrentLoggedInUser().getId());
        if (profile.isPresent()) {
            if (profileObject.getName().isBlank()) {
                throw new InformationInvalidException("Name can not be empty or only contains space character");
            }
            profile.get().setName(profileObject.getName());
            profile.get().setDescription(profileObject.getDescription());
            return profileRepository.save(profile.get());
        } else {
            throw new InformationNotFoundException("You don not have a profile yet");
        }
    }

    /**
     * Get a user's profile by given user id
     *
     * @param userId the id of the user
     * @return Profile
     * @throws InformationNotFoundException If the profile is not existed or the user is not existed
     */
    public Profile getProfileByUserId(Long userId) {
        Optional<Profile> profile = profileRepository.findByUserId(userId);
        if (profile.isPresent()) {
            return profile.get();
        } else {
            throw new InformationNotFoundException("The user with id" + userId + " doesn't have a profile or the user does not exist");
        }
    }
}
