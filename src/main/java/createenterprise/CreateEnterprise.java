package createenterprise;

import com.google.api.services.androidmanagement.v1.AndroidManagement;
import com.google.api.services.androidmanagement.v1.model.Enterprise;
import com.google.api.services.androidmanagement.v1.model.SignupUrl;
import createenterprise.util.Result;
import createenterprise.util.Type;
import java.io.IOException;

public class CreateEnterprise {

    private AndroidManagement androidManagement;

    public CreateEnterprise(AndroidManagement androidManagement) {
        this.androidManagement = androidManagement;
    }

    /** Creates a new enterprise. Returns the enterprise name.
     * @param  googleCloudConsoleProjectID: your Google cloud console project ID
     */
    public Result createEnterprise(String googleCloudConsoleProjectID,
                                   String callBackUrl) throws IOException {

        SignupUrl signupUrl = androidManagement
                .signupUrls()
                .create()
                .setProjectId(googleCloudConsoleProjectID)
                .setCallbackUrl(callBackUrl)
                .execute();

        System.out.print("To sign up for a new enterprise, open this URL in your browser: ");
        System.out.println(signupUrl.getUrl());

        return new Result(signupUrl, null);
    }

    public Result completeEnterpriseCreation(String googleCloudConsoleProjectID,
                                              SignupUrl signupUrl,
                                              String enterpriseToken) throws IOException {

        // Create the enterprise.
        System.out.println("Completing enterprise creation....");
        String enterpriseName = androidManagement
                .enterprises()
                .create(new Enterprise())
                .setProjectId(googleCloudConsoleProjectID)
                .setSignupUrlName(signupUrl.getName())
                .setEnterpriseToken(enterpriseToken)
                .execute()
                .getName();

        System.out.println("Enterprise created successfully with name: " + enterpriseName);
        return new Result(signupUrl, enterpriseName);
    }
}
