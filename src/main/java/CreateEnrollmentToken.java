import com.google.api.services.androidmanagement.v1.AndroidManagement;
import com.google.api.services.androidmanagement.v1.model.EnrollmentToken;

import java.io.IOException;

public class CreateEnrollmentToken {

    /** Creates an enrollment token. */
    public String createEnrollmentToken(AndroidManagement androidManagement, OutputType outputType, String enterpriseName, String policyId)
            throws IOException {
        System.out.println("Creating enrollment token...");
        EnrollmentToken token =
                new EnrollmentToken().setPolicyName(policyId).setDuration("86400s");

        if(outputType == OutputType.URL_STRING) {
            return androidManagement
                    .enterprises()
                    .enrollmentTokens()
                    .create(enterpriseName, token)
                    .execute()
                    .getValue();
        } else {
            return androidManagement
                    .enterprises()
                    .enrollmentTokens()
                    .create(enterpriseName, token)
                    .execute()
                    .getQrCode();
        }
    }


    enum  OutputType {
        QR_CODE, URL_STRING;
    }
}
