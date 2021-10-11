package createenterprise.util;

public class Result {

    private Type resultType;
    private String signUpUrl;
    private String enterpriseName;

    public Result(Type resultType, String signUpUrl, String enterpriseName) {
        this.resultType = resultType;
        this.signUpUrl = signUpUrl;
        this.enterpriseName = enterpriseName;
    }

    public Type getResultType() {
        return resultType;
    }

    public void setResultType(Type resultType) {
        this.resultType = resultType;
    }

    public String getSignUpUrl() {
        return signUpUrl;
    }

    public void setSignUpUrl(String signUpUrl) {
        this.signUpUrl = signUpUrl;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }
}


