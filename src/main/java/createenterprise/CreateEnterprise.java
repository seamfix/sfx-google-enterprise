package createenterprise;

import com.google.api.services.androidmanagement.v1.AndroidManagement;
import com.google.api.services.androidmanagement.v1.model.Enterprise;
import com.google.api.services.androidmanagement.v1.model.SignupUrl;
import createenterprise.util.Result;
import createenterprise.util.Type;
import java.io.IOException;

public class CreateEnterprise {

    private AndroidManagement androidManagement;
    private SignupUrl signupUrl;

    public CreateEnterprise(AndroidManagement androidManagement) {
        this.androidManagement = androidManagement;
    }

    /** Creates a new enterprise. Returns the enterprise name.
     * @param  googleCloudConsoleProjectID: your Google cloud console project ID
     * @param enterpriseToken: the token value returned after the user has completed enterprise creation with Google. If
     * you set this value to null, we will return a redirect result which means that the user should be redirected to
     * Google enterprise creation page to create his enterprise.
     * */
    public Result createEnterprise(String googleCloudConsoleProjectID,
                                   String callBackUrl,
                                   String enterpriseToken) throws IOException {

        signupUrl = androidManagement
                .signupUrls()
                .create()
                .setProjectId(googleCloudConsoleProjectID)
                .setCallbackUrl(callBackUrl)
                .execute();

        if (enterpriseToken == null || enterpriseToken.isEmpty()) { //user has not signed up with Google.
            System.out.print("To sign up for a new enterprise, open this URL in your browser: ");
            System.out.println(signupUrl.getUrl());

            return new Result(Type.REDIRECT, signupUrl.getUrl(), null);
        } else {
            return completeEnterpriseCreation(googleCloudConsoleProjectID, signupUrl, enterpriseToken);
        }
    }

    private Result completeEnterpriseCreation(String googleCloudConsoleProjectID,
                                              SignupUrl signupUrl,
                                              String enterpriseToken) throws IOException {

        // Create the enterprise.
        System.out.println("Creating enterprise...");
        String enterpriseName = androidManagement
                .enterprises()
                .create(new Enterprise())
                .setProjectId(googleCloudConsoleProjectID)
                .setSignupUrlName(signupUrl.getName())
                .setEnterpriseToken(enterpriseToken)
                .execute()
                .getName();

        System.out.println("Enterprise created successfully with name: " + enterpriseName);
        return new Result(Type.SUCCESS, signupUrl.getUrl(), enterpriseName);
    }
}
