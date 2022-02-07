/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.databaseRepository;

import java.security.MessageDigest;

/**
 *
 * @author chris
 */
public class SecurityMethods {
    public String hexString(String _stringToHex) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        md.update(_stringToHex.getBytes());

        byte[] stringBytes = md.digest();

        StringBuilder hexxedString = new StringBuilder(2 * stringBytes.length);
        for (int i = 0; i < stringBytes.length; i++) {

            String stringBeingHexxed = Integer.toHexString(0xff & stringBytes[i]);

            if (stringBeingHexxed.length() == 1) {
                hexxedString.append('0');
            }
            hexxedString.append(stringBeingHexxed);
        }

        return hexxedString.toString();
    }
}
