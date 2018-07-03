package jamfprocleaner;

import static java.lang.System.exit;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author ZD
 */
public class JamfProCleaner {

    private static JSSapiCalls api;

    public static void main(String[] args) {

        try {


 
            
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter the Jamf Pro URL:");
            String url = scanner.next();
            System.out.println("Please enter the Jamf Pro Username:");
            String username = scanner.next();
            System.out.println("Please enter the Jamf Pro Password:");
            String password = scanner.next();
            
            api = new JSSapiCalls(url, username, password, JSSapiCalls.FORMAT.JSON, JSSapiCalls.FORMAT.JSON);
            System.out.println("Press 1 if we would like to get the policy names and id numbers.");
            System.out.println("Press 2 if we know them already.");
            int choice = scanner.nextInt();

            if (choice == 1) {
                JSONObject jsonPolicy = new JSONObject(api.get("policies"));
                JSONArray jsonPolicyArray = jsonPolicy.getJSONArray("policies");
                for (int i = 0; i < jsonPolicyArray.length(); i++) {
                    JSONObject dataObj = (JSONObject) jsonPolicyArray.get(i);
                    int id = dataObj.getInt("id");
                    String name = dataObj.getString("name");
                    System.out.println("Policy: " + name + "\n ID: " + id);
                }
                System.out.println("Please enter the ID numbers of the polcies you would like to delete followed by the enter key.\n Enter 000 to end the list");
                ArrayList<Integer> array = new ArrayList<>();


                int policies;
                policies = scanner.nextInt();
                while (policies != 000 ) {
                    array.add(policies);
                    policies = scanner.nextInt();

                } 

                System.out.println("We are deleting policies: ");

                array.forEach((array1) -> {
                    System.out.println(array1);
                });


                System.out.println("Press 1 to confirm, 2 if they are not correct. 2 will close the program");
                int choice2 = scanner.nextInt();

                if (choice2 == 1) {
                    array.forEach((array1) -> {
                        try {
                            api.delete("policies/id/" + array1);
                        } catch (JssApiException ex) {
                            Logger.getLogger(JamfProCleaner.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });

                }

                if (choice2 == 2) {
                    exit(0);
                }
            }
            if (choice == 2) {
                System.out.println("Please enter the ID numbers of the polcies you would like to delete followed by the enter key.\n Enter 0 to end the list");
                ArrayList<Integer> array = new ArrayList<>();

                int policies;
                policies = scanner.nextInt();
                while (policies != 000 ) {
                    array.add(policies);
                    policies = scanner.nextInt();

                } 

                System.out.println("We are deleting policies: ");

                array.forEach((_item) -> {
                            int count = 0;
                            System.out.println(array.get(count));
                            count++;
                });

                System.out.println("Press 1 to confirm, 2 if they are not correct. 2 will close the program");
                int choice2 = scanner.nextInt();

                if (choice2 == 1) {
                    array.forEach((_item) -> {
                        try {
                            int count = 0;
                            api.delete("policies/id/" + array.get(count));
                            count++;
                        } catch (JssApiException ex) {
                            Logger.getLogger(JamfProCleaner.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });

                }

                else if (choice2 == 2) {
                    exit(0);
                }

            } else {
                System.out.println("Please choose 1 or 2 ");
            }
System.out.println("Deletion sucessful!");
        } catch (JssApiException | JSONException ex) {
            Logger.getLogger(JamfProCleaner.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
