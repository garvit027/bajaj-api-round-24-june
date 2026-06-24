package com.bfhl.service.impl;

import com.bfhl.dto.BfhlRequest;
import com.bfhl.dto.BfhlResponse;
import com.bfhl.service.BfhlService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class BfhlServiceImpl implements BfhlService {

    @Value("${bfhl.user-id}")
    private String userId;

    @Value("${bfhl.email}")
    private String email;

    @Value("${bfhl.roll-number}")
    private String rollNumber;

    @Override
    public BfhlResponse processRequest(BfhlRequest request) {
        BfhlResponse response = new BfhlResponse();
        response.setUserId(userId);
        response.setEmail(email);
        response.setRollNumber(rollNumber);

        if (request == null || request.getData() == null) {
            response.setIsSuccess(false);
            response.setOddNumbers(new ArrayList<>());
            response.setEvenNumbers(new ArrayList<>());
            response.setAlphabets(new ArrayList<>());
            response.setSpecialCharacters(new ArrayList<>());
            response.setSum("0");
            response.setConcatString("");
            return response;
        }

        List<String> oddNumbers = new ArrayList<>();
        List<String> evenNumbers = new ArrayList<>();
        List<String> alphabets = new ArrayList<>();
        List<String> specialCharacters = new ArrayList<>();
        BigInteger sum = BigInteger.ZERO;
        List<Character> allAlphaChars = new ArrayList<>();

        for (String item : request.getData()) {
            if (item == null) {
                continue;
            }
            if (item.matches("-?\\d+")) {
                try {
                    BigInteger number = new BigInteger(item);
                    if (number.testBit(0)) {
                        oddNumbers.add(item);
                    } else {
                        evenNumbers.add(item);
                    }
                    sum = sum.add(number);
                } catch (Exception e) {
                    specialCharacters.add(item);
                }
            } else if (item.matches("[a-zA-Z]+")) {
                alphabets.add(item.toUpperCase());
                for (char ch : item.toCharArray()) {
                    allAlphaChars.add(ch);
                }
            } else {
                specialCharacters.add(item);
                for (char ch : item.toCharArray()) {
                    if (Character.isLetter(ch)) {
                        allAlphaChars.add(ch);
                    }
                }
            }
        }

        Collections.reverse(allAlphaChars);
        StringBuilder concatString = new StringBuilder();
        for (int i = 0; i < allAlphaChars.size(); i++) {
            char ch = allAlphaChars.get(i);
            if (i % 2 == 0) {
                concatString.append(Character.toUpperCase(ch));
            } else {
                concatString.append(Character.toLowerCase(ch));
            }
        }

        response.setIsSuccess(true);
        response.setOddNumbers(oddNumbers);
        response.setEvenNumbers(evenNumbers);
        response.setAlphabets(alphabets);
        response.setSpecialCharacters(specialCharacters);
        response.setSum(sum.toString());
        response.setConcatString(concatString.toString());

        return response;
    }
}
