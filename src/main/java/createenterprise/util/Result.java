package createenterprise.util;

import com.google.api.services.androidmanagement.v1.model.SignupUrl;

public class Result {

    private SignupUrl signUpUrl;
    private String enterpriseName;

    public Result(SignupUrl signUpUrl, String enterpriseName) {
        this.signUpUrl = signUpUrl;
        this.enterpriseName = enterpriseName;
    }

    public SignupUrl getSignUpUrl() {
        return signUpUrl;
    }

    public void setSignUpUrl(SignupUrl signUpUrl) {
        this.signUpUrl = signUpUrl;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }
}


