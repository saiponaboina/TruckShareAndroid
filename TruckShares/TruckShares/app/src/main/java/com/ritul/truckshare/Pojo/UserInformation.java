package com.ritul.truckshare.Pojo;

import com.google.gson.InstanceCreator;

import java.lang.reflect.Type;

/**
 * Created by admin on 09-Feb-16.
 */
public class UserInformation implements InstanceCreator<UserInformation> {

    String appUserId, firstName,lastName,fullName,userName,emailAddress,ssnNumber,
            mobileNumber,profileImageBase64String, profileImageFormat,userBankDetailId,
            accountType,accountHolderName,bankRoutingNumber,bankAccountNumber,userCreditCardDetailId,
            creditCardType,fullNameOnCard,creditCardNumber,expiration,cvvCode,zipCode,appUserType;

    public String getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(String appUserId) {
        this.appUserId = appUserId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getSsnNumber() {
        return ssnNumber;
    }

    public void setSsnNumber(String ssnNumber) {
        this.ssnNumber = ssnNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getProfileImageBase64String() {
        return profileImageBase64String;
    }

    public void setProfileImageBase64String(String profileImageBase64String) {
        this.profileImageBase64String = profileImageBase64String;
    }

    public String getProfileImageFormat() {
        return profileImageFormat;
    }

    public void setProfileImageFormat(String profileImageFormat) {
        this.profileImageFormat = profileImageFormat;
    }

    public String getUserBankDetailId() {
        return userBankDetailId;
    }

    public void setUserBankDetailId(String userBankDetailId) {
        this.userBankDetailId = userBankDetailId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getBankRoutingNumber() {
        return bankRoutingNumber;
    }

    public void setBankRoutingNumber(String bankRoutingNumber) {
        this.bankRoutingNumber = bankRoutingNumber;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getUserCreditCardDetailId() {
        return userCreditCardDetailId;
    }

    public void setUserCreditCardDetailId(String userCreditCardDetailId) {
        this.userCreditCardDetailId = userCreditCardDetailId;
    }

    public String getCreditCardType() {
        return creditCardType;
    }

    public void setCreditCardType(String creditCardType) {
        this.creditCardType = creditCardType;
    }

    public String getFullNameOnCard() {
        return fullNameOnCard;
    }

    public void setFullNameOnCard(String fullNameOnCard) {
        this.fullNameOnCard = fullNameOnCard;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getCvvCode() {
        return cvvCode;
    }

    public void setCvvCode(String cvvCode) {
        this.cvvCode = cvvCode;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAppUserType() {
        return appUserType;
    }

    public void setAppUserType(String appUserType) {
        this.appUserType = appUserType;
    }


    @Override
    public UserInformation createInstance(Type type) {
        return new UserInformation();
    }
}
