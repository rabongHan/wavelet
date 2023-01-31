import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler_search implements URLHandler{
    ArrayList<String> strs = new ArrayList<String>();
    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("Welcome to Jaewon's String Server");
        } else if (url.getPath().contains("/add-message")) {
            String[] parameters = url.getQuery().split("=");
            if (parameters[0].equals("s")) {
                strs.add(parameters[1]);
                String str_to_return = "";
                for(String str:strs) {
                    str_to_return += str + "\n";
                }
                return str_to_return;
            }
            return String.format("Use path '/add?s=<your-word>'");
        }else {
            System.out.println("Path: " + url.getPath());
            return "404 Not Found!";
        }
    }
}

class StringServer {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler_search());
    }
}
