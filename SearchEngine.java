import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler_search implements URLHandler{
    ArrayList<String> strs = new ArrayList<String>();
    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("Welcome to Jaewon's Search Engine");
        } else if (url.getPath().contains("/add")) {
            // If there is search history in web browser that I added certain words
            // and I try to add that newly after resetting the web server, because of
            // search history, the path is automatically filled so that making addition 
            // twice --> How to solve? [Temporarily, I just add If statement so that only
            // one element of same name can be added]
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                for(String str : strs) {
                    if(str.equals(parameters[1]))
                        return String.format("%s is already added",parameters[1]);
                }
                strs.add(parameters[1]);
                return String.format("%s is newly added",parameters[1]);
            }
            return String.format("Use path '/add?s=<your-word>'");
        } else if (url.getPath().contains("/search")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                String sub_str = parameters[1];
                ArrayList<String> str_to_return = new ArrayList<String>();
                for(String str : strs) {
                    if(str.contains(sub_str))
                        str_to_return.add(str);
                }
                return String.format("result: %s",str_to_return.toString());
            }
            return String.format("Use path '/search?s=<your-word(substring)>'");
        } else {
            System.out.println("Path: " + url.getPath());
            return "404 Not Found!";
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler_search());
    }
}
