import com.google.api.services.androidmanagement.v1.AndroidManagement;
import com.google.api.services.androidmanagement.v1.model.Enterprise;
import com.google.api.services.androidmanagement.v1.model.SignupUrl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CreateEnterprise {

    /** Creates a new enterprise. Returns the enterprise name. */
    public String createEnterprise(AndroidManagement androidManagementClient, String projectId) throws IOException {
        // Initiate signup process.
        System.out.println("Creating signup URL...");
        SignupUrl signupUrl =
                androidManagementClient
                        .signupUrls()
                        .create()
                        .setProjectId(projectId)
                        .setCallbackUrl("https://localhost:9999")
                        .execute();
        System.out.print(
                "To sign up for a new enterprise, open this URL in your browser: ");
        System.out.println(signupUrl.getUrl());
        System.out.println(
                "After signup, you will see an error page in the browser.");
        System.out.print(
                "Paste the enterpriseToken value from the error page URL here: ");
        String enterpriseToken =
                new BufferedReader(new InputStreamReader(System.in)).readLine();

        // Create the enterprise.
        System.out.println("Creating enterprise...");
        String enterpriseName = androidManagementClient
                .enterprises()
                .create(new Enterprise())
                .setProjectId(projectId)
                .setSignupUrlName(signupUrl.getName())
                .setEnterpriseToken(enterpriseToken)
                .execute()
                .getName();

        System.out.println("Enterprise created with name: " + enterpriseName);
        return enterpriseName;
    }
}
