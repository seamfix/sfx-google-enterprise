import com.google.api.services.androidmanagement.v1.AndroidManagement;
import com.google.api.services.androidmanagement.v1.model.Policy;

import java.io.IOException;

public class CreatePolicy {

    private AndroidManagement androidManagement;

    public CreatePolicy(AndroidManagement androidManagement) {
        this.androidManagement = androidManagement;
    }

    /** Sets the policy of the given id to the given value. */
    public void setPolicy(String enterpriseName, String policyId, Policy policy)
            throws IOException {
        System.out.println("Setting policy...");
        String name = enterpriseName + "/policies/" + policyId;
        androidManagement
                .enterprises()
                .policies()
                .patch(name, policy)
                .execute();
        System.out.println("Setting policy completed!");
    }
}
