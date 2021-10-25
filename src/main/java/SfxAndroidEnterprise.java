import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.androidmanagement.v1.AndroidManagement;
import com.google.api.services.androidmanagement.v1.model.Device;
import com.google.api.services.androidmanagement.v1.model.ListDevicesResponse;
import com.google.api.services.androidmanagement.v1.model.Policy;
import com.google.api.services.androidmanagement.v1.model.SignupUrl;
import createenterprise.CreateEnterprise;
import createenterprise.util.Result;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SfxAndroidEnterprise {

    /** The OAuth scope for the Android Management API. */
    private static final String OAUTH_SCOPE =
            "https://www.googleapis.com/auth/androidmanagement";

    private static AndroidManagement androidManagement;

    /** Builds an Android Management API client. */
    private static AndroidManagement getAndroidManagementClient(String serviceAccountCredentialFilePath , String appName)
            throws IOException, GeneralSecurityException {
        try (FileInputStream input = new FileInputStream(serviceAccountCredentialFilePath)) {
            GoogleCredential credential = GoogleCredential.fromStream(input).createScoped(Collections.singleton(OAUTH_SCOPE));
            return new AndroidManagement.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    JacksonFactory.getDefaultInstance(),
                    credential
            ).setApplicationName(appName).build();
        }
    }

    public static void init(String serviceAccountCredentialFilePath,
                            String appName) throws IOException, GeneralSecurityException {
        androidManagement = getAndroidManagementClient(serviceAccountCredentialFilePath, appName);
    }

    /** Creates a new enterprise.
     * @param  googleCloudConsoleProjectID: your Google cloud console project ID
     * @param callBackUrl: The url of the website google should redirect to when the user has created an enterprise.
     */
    public static Result createEnterprise(String googleCloudConsoleProjectID, String callBackUrl) throws IOException {
        CreateEnterprise createEnterprise = new CreateEnterprise(androidManagement);
        return createEnterprise.createEnterprise(googleCloudConsoleProjectID, callBackUrl);
    }

    /**
     * This process will get the name of the enterprise.
     * ***/
    public static Result completeEnterpriseCreation(String googleCloudConsoleProjectID,
                                                           SignupUrl signupUrl,
                                                           String enterpriseToken) throws IOException  {
        CreateEnterprise createEnterprise = new CreateEnterprise(androidManagement);
        return createEnterprise.completeEnterpriseCreation(googleCloudConsoleProjectID, signupUrl, enterpriseToken);
    }

    public static void createPolicy(String enterpriseName, String policyId, Policy policy) throws IOException {
        CreatePolicy createPolicy = new CreatePolicy(androidManagement);
        createPolicy.setPolicy(enterpriseName, policyId, policy);
    }

    /***
     * Creates an enrollment token.
     * @param outputType: Determines the kind of output result. QR_CODE for QR code and URL_STRING for a url.
     * @param enterpriseName: The name of your enterprise.
     * @param policyId: The ID of the policy you created.
     * @return either a QR code payload or a URL enrollment string.
     * @throws IOException
     */
    public static String createEnrollmentToken(CreateEnrollmentToken.OutputType outputType,
                                               String enterpriseName,
                                               String policyId) throws IOException {

        CreateEnrollmentToken createEnrollmentToken = new CreateEnrollmentToken(androidManagement);
        return createEnrollmentToken.createEnrollmentToken(outputType, enterpriseName, policyId);
    }

    /**
     * Returns a list of all devices enrolled under the enterprise.
     **/
    public static List<Device> getAllDevices(String enterpriseName) throws IOException {
        ListDevicesResponse response = androidManagement
                        .enterprises()
                        .devices()
                        .list(enterpriseName)
                        .execute();
        return response.getDevices() == null
                ? new ArrayList<Device>() : response.getDevices();
    }


    public static AndroidManagement getAndroidManagement() {
        return androidManagement;
    }

    public static void setAndroidManagement(AndroidManagement androidMngt) {
        androidManagement = androidMngt;
    }
}
