package imager.main;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import helpers.PropertyHelper;

public class ImagerMain {
    private static List<String> files = new ArrayList<String>();

    public static void main(String args[]) {
        files.add("/home/npriyanshu/Desktop/Images/20190420_052845.jpg");
        files.add("/home/npriyanshu/Desktop/Images/20200407_184756.jpg");
        files.add("/home/npriyanshu/Desktop/Images/20200414_211138.jpg");
        JSONArray jsonArray = PropertyHelper.readFileProperties(files);
        for (Object object : jsonArray) {
            JSONObject jsonObject = (JSONObject) object;
            System.out.println(jsonObject.toJSONString());
        }
    }
}