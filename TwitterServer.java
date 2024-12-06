import java.io.*;
import java.util.*;
import javax.swing.ImageIcon;
import java.net.*;

/**
 * Twitter Server 
 * <p>
 * Purdue University -- CS18000 -- Team Project
 *
 * @author Yajushi
 * @version Nov 14, 2024
 */

public class TwitterServer implements Runnable {
    Socket socket;
    private ArrayList<User> users = new ArrayList<User>();
    private ArrayList<Post> posts = new ArrayList<Post>();
    private final Object obj = new Object();

    public TwitterServer(String usernameFile, ArrayList<String> userFiles, ArrayList<String> postFiles, Socket socket) {
        readFile(usernameFile, userFiles, postFiles);
        this.socket = socket;
    }
    
    public TwitterServer(Socket socket) {
        this.socket = socket;
    }

    

    public void writeFile() {
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(new File("users.txt"), false));
            synchronized (obj) {
                for (User u : users) { // Write users
                    u.writeFile(); //write specifc user info
                    String picture = (u.getProfilePicture() == null) ? "null" : u.getProfilePicture();
                    String userInfo = u.getName() + ", " + u.getUsername() + ", " + u.getPassword() + ", " + picture;
                    pw.println(userInfo);
                }
            }
            pw.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not created");
        }
        synchronized (obj) {
            for (Post p : posts) { //write posts
                p.writePost();
            }
        }
    }
    public void readFile(String usernameFile, ArrayList<String> userFiles, ArrayList<String> postFiles) {
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(new File(usernameFile)));
            while (true) { //read users
                String userInfo = bfr.readLine();
                if (userInfo == null) {
                    break;
                }
                String[] uArray = userInfo.split(", ");
                String firstname = uArray[0].substring(0, uArray[0].indexOf(" "));
                String lastname = uArray[0].substring(uArray[0].indexOf(" ") + 1);
                String image = (uArray[3].equals("null")) ? null : uArray[3];
                synchronized (obj) {
                    users.add(new User(firstname, lastname, uArray[1], uArray[2], image));
                }
            }

            for (String file : userFiles) { //get specific user info
                bfr = new BufferedReader(new FileReader(new File(file)));
                String username = bfr.readLine();
                User user = getUser(username);

                ArrayList<User> friends = new ArrayList<User>();
                bfr.readLine();
                username = bfr.readLine();
                while (username.equals("BLOCKED") == false) {
                    friends.add(getUser(username));
                    username = bfr.readLine();
                }

                ArrayList<User> blocked = new ArrayList<User>();
                username = bfr.readLine();
                while (username != null) {
                    blocked.add(getUser(username));
                    username = bfr.readLine();
                }

                user.setFriends(friends);
                user.setBlocked(blocked);
            }

            for (String file : postFiles) { //get post info
                bfr = new BufferedReader(new FileReader(new File(file)));
                String postInfo = bfr.readLine();
                String[] postArray = postInfo.split(", ");
                String im = (postArray[1].equals("null")) ? null : (postArray[1]);
                Post p = new Post(postArray[0], im, getUser(postArray[4]),
                                  Integer.parseInt(postArray[2]), Integer.parseInt(postArray[3]));
                synchronized (obj) {
                    posts.add(p);
                }
                while (true) {
                    String commentInfo = bfr.readLine(); //get comment info
                    if (commentInfo == null) {
                        break;
                    }
                    String[] commentArray = commentInfo.split(", ");
                    p.addComment(commentArray[0], p.getUser(), getUser(commentArray[2]), p);

                }
            }

            bfr.close();
        } catch (Exception e) {
            System.out.println("Error: Files could not be read");
        }
    }
    public User getUser(String username) {
        User u = null;
        synchronized (obj) {
            for (User user : users) {
                if (user.getUsername().equals(username)) {
                    u = user;
                    break;
                }
            }
        }
        return u;
    }

    public String option1(String friendUsername, User user) { //add friend
        User friend = getUser(friendUsername);
        if (friend == null) {
            return "Error: Invalid username";
        } else {
            user.addFriend(friend);
            return "Friend added!";
        }
    }
    public String option2(String friendUsername, User user) { //remove friend
        User friend = getUser(friendUsername);
        if (friend == null) {
            return "Error: Invalid username";
        } else {
            user.removeFriend(friend);
            return "Friend removed.";
        }
    }
    public String option3(String blockedUsername, User user) { //block user
        User blocked = getUser(blockedUsername);
        if (blocked == null) {
            return "Error: Invalid username";
        } else {
            user.blockUser(blocked);
            return "User blocked.";
        }
    }
    public String option4(String blockedUsername, User user) { //unblock user
        User blocked = getUser(blockedUsername);
        if (blocked == null) {
            return "Error: Invalid username";
        } else {
            user.unblock(blocked);
            return "User unblocked.";
        }
    }
    public String option5(String userprofile) { //user profile
        User other = getUser(userprofile);
        if (other == null) {
            return "Error: Invalid username\nstop";
        } else {
            String toReturn = other.toString() + "\nstop";
            return toReturn;
        }
    }
    public String option6(User user) { //display feed
        ArrayList<Post> feed;
        synchronized (obj) {
            feed = user.displayFeed(posts);
        }   
        if (feed.size() == 0) {
            return "There are no posts in your feed.\nstop";
        }
        String toReturn = "";
        for (Post p : feed) {
            toReturn += p.toString() + "\n";
        }
        toReturn = toReturn.substring(0, toReturn.length() - 1) + "\nstop";
        return toReturn;
    }
    public void option7(User user, String caption, String path) throws InvalidPostException { //create post
        String image;
        if (path == null) {
            image = null;
        } else {
            image = path;
        }
        Post p = new Post(caption, image, user);
        synchronized (obj) {
            posts.add(p);
        }
    }
    public String option8(int postNum, User user) { //delete post
        if (postNum < 0 || postNum >= posts.size()) {
            return "Error: Post could not be found";
        } else {
            Post p = posts.get(postNum);
            if (p.getUser().equals(user) == false) {
                return "Error: You do not have the permissions to delete this post";
            } else {
                posts.remove(p);
                return "Post deleted";
            }
        }
    }
    public String option9(int postNum, String caption, User user) { //edit post
        if (postNum < 0 || postNum >= posts.size()) {
            return "Error: Post could not be found";
        } else {
            Post p = posts.get(postNum);
            if (p.getUser().equals(user) == false) {
                return "Error: You do not have the permissions to edit this post";
            } else {
                if (caption == null || caption.length() == 0) {
                    return "Error: Invalid caption";
                } else {
                    p.editPost(caption);
                    return "Post edited";
                }
                
            }
        }
    }
    public String option10(int postNum, String comment, User user) { //create comment
        if (postNum < 0 || postNum >= posts.size()) {
            return "Error: Post could not be found";
        } else {
            Post p = posts.get(postNum);
            if (comment == null || comment.length() == 0) {
                return "Error: Invalid comment";
            } else {
                p.addComment(comment, p.getUser(), user, p);
                return "Comment created";
            }
        }
    }
    public String option11(int postNum, int commentNum, User user) { //delete comment
        if (postNum < 0 || postNum >= posts.size()) {
            return "Error: Post could not be found";
        } else {
            Post p = posts.get(postNum);
            if (commentNum < 0 || commentNum >= p.getComments().size()) {
                return "Error: Comment could not be found";
            } else {
                Comment comment = p.getComments().get(commentNum);
                if (comment.getCommenter().equals(user) == false && comment.getPostOwner().equals(user) == false) {
                    return "Error: You do not have the permissions to delete this comment";
                } else {
                    p.getComments().remove(comment);
                    return "Comment deleted";
                }
            }
        }
    }
    public String option12(int postNum, int commentNum, String newComment, User user) { //edit comment
        if (postNum < 0 || postNum >= posts.size()) {
            return "Error: Post could not be found";
        } else {
            Post p = posts.get(postNum);
            if (commentNum < 0 || commentNum >= p.getComments().size()) {
                return "Error: Comment could not be found";
            } else {
                Comment comment = p.getComments().get(commentNum);
                if (comment.getCommenter().equals(user) == false) {
                    return "Error: You do not have the permissions to edit this comment";
                } else {
                    if (newComment == null || newComment.length() == 0) {
                        return "Error: Invalid comment";
                    } else {
                        comment.setComment(newComment);
                        return "Comment edited";
                    }
                }
            }
        }
    }
    public String option13(int postNum) { //upvote post
        if (postNum < 0 || postNum >= posts.size()) {
            return "Error: Post could not be found";
        } else {
            Post p = posts.get(postNum);
            p.incrementUpvote();
            return "Post upvoted";
        }
    }
    public String option14(int postNum) { //downvote post
        if (postNum < 0 || postNum >= posts.size()) {
            return "Error: Post could not be found";
        } else {
            Post p = posts.get(postNum);
            p.incrementDownvote();
            return "Post downvoted";
        }
    }
    public String option15(String oldPass, String newPass, User user) { //change password
        return (user.setPassword(oldPass, newPass)) ? "Password changed" : "Could not change password";
    }
    public void option16() { //end run
        writeFile();
    }

    public void run() {
        if (true) {
            try {
                User user = null;
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter writer = new PrintWriter(socket.getOutputStream());
                String option;
                while ((option = reader.readLine()) != null) {
                    if (option.equals("Login")) {
                        String username = reader.readLine();
                        User tempUser = getUser(username); 
                        boolean tempUserNull = (tempUser != null);
                        writer.write(String.valueOf(tempUserNull));
                        writer.println();
                        writer.flush();

                        if (tempUser != null) {
                            String password = reader.readLine();
                            boolean pass = tempUser.getPassword().equals(password);
                            writer.write(String.valueOf(pass));
                            writer.println();
                            writer.flush();

                            if (pass) {
                                user = tempUser;
                            }
                        }
                    }
                    if (option.equals("Sign Up")) {
                        String firstName = reader.readLine();
                        String lastName = reader.readLine();
                        String username = reader.readLine();
                        User tempUser = getUser(username);
                        boolean tempUserNull = (tempUser == null);
                        System.out.println(tempUserNull);
                        writer.write(String.valueOf(tempUserNull));
                        writer.println();
                        writer.flush();

                        if (tempUserNull) {
                            String password = reader.readLine();
                            boolean validPass = password.length() >= 7 && password.length() <= 12;
                            writer.write(String.valueOf(validPass));
                            writer.println();
                            writer.flush();
                            String pfp = reader.readLine();
                            String image = (pfp.equals("null")) ? null : pfp;

                            if (validPass) {
                                user = new User(firstName, lastName, username, password, image);
                                users.add(user);
                            }

                        }

                    }
                    if (option.equals("Option 1")) {
                        String friendUsername = reader.readLine();
                        String output = option1(friendUsername, user);
                        writer.write(output);
                        writer.println();
                        writer.flush();
                    }
                    if (option.equals("Option 2")) {
                        String removeUsername = reader.readLine();
                        String output = option2(removeUsername, user);
                        writer.write(output);
                        writer.println();
                        writer.flush();
                    }
                    if (option.equals("Option 3")) {
                        String blockUsername = reader.readLine();
                        String output = option3(blockUsername, user);
                        writer.write(output);
                        writer.println();
                        writer.flush();
                        
                    }
                    if (option.equals("Option 4")) {
                        String unblockUsername = reader.readLine();
                        String output = option4(unblockUsername, user);
                        writer.write(output);
                        writer.println();
                        writer.flush();
                    }
                    if (option.equals("Option 5")) {
                        String userProfile = reader.readLine();
                        String output = option5(userProfile);
                        writer.write(output);
                        writer.println();
                        writer.flush();
                    }
                    if (option.equals("Option 6")) {
                        String output = option6(user);
                        writer.write(output);
                        writer.println();
                        writer.flush();
                    }
                    if (option.equals("Option 7")) {
                        String caption = reader.readLine();
                        String path = reader.readLine();
                        try {
                            option7(user, caption, path);
                        } catch (InvalidPostException e) {
                            System.out.println("Error: Invalid Post");
                        }
                        
                    }
                    if (option.equals("Option 8")) {
                        int postNum = Integer.parseInt(reader.readLine());
                        String output = option8(postNum, user);
                        writer.write(output);
                        writer.println();
                        writer.flush();
                    }
                    if (option.equals("Option 9")) {
                        int postNum = Integer.parseInt(reader.readLine());
                        String caption = reader.readLine();
                        String output = option9(postNum, caption, user);
                        writer.write(output);
                        writer.println();
                        writer.flush();
                    }
                    if (option.equals("Option 10")) {
                        int postNum = Integer.parseInt(reader.readLine());
                        String comment = reader.readLine();
                        String output = option10(postNum, comment, user);
                        writer.write(output);
                        writer.println();
                        writer.flush();
                    }
                    if (option.equals("Option 11")) {
                        int postNum = Integer.parseInt(reader.readLine());
                        int commentNum = Integer.parseInt(reader.readLine());
                        String output = option11(postNum, commentNum, user);
                        writer.write(output);
                        writer.println();
                        writer.flush();
                    }
                    if (option.equals("Option 12")) {
                        int postNum = Integer.parseInt(reader.readLine());
                        int commentNum = Integer.parseInt(reader.readLine());
                        String newComment = reader.readLine();
                        String output = option12(postNum, commentNum, newComment, user);
                        writer.write(output);
                        writer.println();
                        writer.flush();
                    }
                    if (option.equals("Option 13")) {
                        int postNum = Integer.parseInt(reader.readLine());
                        String output = option13(postNum);
                        writer.write(output);
                        writer.println();
                        writer.flush();
                    }
                    if (option.equals("Option 14")) {
                        int postNum = Integer.parseInt(reader.readLine());
                        String output = option14(postNum);
                        writer.write(output);
                        writer.println();
                        writer.flush();
                    }
                    if (option.equals("Option 15")) {
                        String oldPass = reader.readLine();
                        String newPass = reader.readLine();
                        String output = option15(oldPass, newPass, user);
                        writer.write(output);
                        writer.println();
                        writer.flush();
                    }
                    if (option.equals("Option 16")) {
                        option16();
                        socket.close();
                        break;
                    }                
                } 
            } catch (IOException e) {
                System.out.println("Error: Could not read values from client");
            }

        }
    }
    
    

    public static void main(String[] args) throws IOException {
        try {

            ServerSocket serverSocket = new ServerSocket(4242);

            while (true) {
                Socket socket = serverSocket.accept();
                TwitterServer ts = new TwitterServer(socket);
                new Thread(ts).start();
            }

        } catch (IOException e) {
            System.out.println("Error: Could not create server-client connection");
        }

    }
}
