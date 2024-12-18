
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.*;
/**
 * Client Class
 * <p>
 * Purdue University -- CS18000 -- Phase 2
 *
 * @author Nathan Wan
 * @version Nov 17, 2024
 * */

public class Client implements Runnable {
    private static final String[] MENU = new String[]{"1 - Add a friend",
            "2 - Remove a friend", "3 - Block a user", "4 - Unblock a user",
            "5 - View a user profile", "6 - View feed",
            "7 - Create a post", "8 - Delete a post",
            "9 - Edit a post", "10 - Create a comment",
            "11 - Delete a comment", "12 - Edit a comment",
            "13 - Upvote a post",
            "14 - Downvote a post", "15 - Change password", "16 - Exit"};
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private String pfpPath = "";


    public void run() {
        Scanner s = new Scanner(System.in);
        String hostName = "localhost";
        int portNumber = 4242;
        // Port is 4242
        Socket socket = null;
        BufferedReader reader = null;
        PrintWriter writer = null;
        try {
            socket = new Socket(hostName, portNumber);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());
            System.out.println("Connection Established");
            frame = new JFrame("Twitter Client");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 1000);
            frame.setLocationRelativeTo(null);

            cardLayout = new CardLayout();
            cardPanel = new JPanel(cardLayout);

            cardPanel.add(createWelcomePage(), "WelcomePage");
            cardPanel.add(createLoginPage(), "LoginPage");
            cardPanel.add(createSignUpPage(), "SignUpPage");

            frame.add(cardPanel);
            frame.setVisible(true);
            boolean runLoop = true;
            connectToServer();


            while (runLoop) {

                int action = s.nextInt();


                switch(action) {
                    case 1:
                        writer.write("Option 1");
                        writer.println();
                        writer.flush();
                        //String friendUsername = addOrRemoveFriend(0);
                        System.out.println("Enter the username of the user you would like to add");
                        String friendUsername = s.nextLine();
                        writer.write(friendUsername);
                        writer.println();
                        writer.flush();
                        String valid = reader.readLine();
                        System.out.println(valid);


                        break;
                    case 2:
                        writer.write("Option 2");
                        writer.println();
                        writer.flush();
                        //String removeUsername = addOrRemoveFriend(1);
                        System.out.println("Enter the username of the user you would like to remove");
                        String removeUsername = s.nextLine();
                        writer.write(removeUsername);
                        writer.println();
                        writer.flush();
                        String validRemoval = reader.readLine();
                        System.out.println(validRemoval);
                        break;
                    case 3:
                        writer.write("Option 3");
                        writer.println();
                        writer.flush();
                        //String blockUsername = blockOrRemoveUser(0);
                        System.out.println("Enter the username of the user you would like to block");
                        String blockUsername = s.nextLine();
                        writer.write(blockUsername);
                        writer.println();
                        writer.flush();
                        String validBlock = reader.readLine();
                        System.out.println(validBlock);
                        break;
                    case 4:
                        writer.write("Option 4");
                        writer.println();
                        writer.flush();
                        //String unblockUsername = blockOrRemoveUser(1);
                        System.out.println("Enter the username of the user you would like to unblock");
                        String unblockUsername = s.nextLine();
                        writer.write(unblockUsername);
                        writer.println();
                        writer.flush();
                        String validunBlock = reader.readLine();
                        System.out.println(validunBlock);
                        break;
                    case 5:
                        writer.write("Option 5");
                        writer.println();
                        writer.flush();
                        //String viewUser = viewProfile();
                        System.out.println("Enter the username of the profile you would like to view");
                        String viewUser = s.nextLine();
                        writer.write(viewUser);
                        writer.println();
                        writer.flush();
                        String line = reader.readLine();
                        while (line.equals("stop") == false) {
                            System.out.println(line);
                            line = reader.readLine();


                        }
                        break;
                    case 6:
                        writer.write("Option 6");
                        writer.println();
                        writer.flush();
                        String line2 = reader.readLine();
                        while (line2.equals("stop") == false) {
                            System.out.println(line2);
                            line2 = reader.readLine();


                        }
                        break;
                    case 7:
                        writer.write("Option 7");
                        writer.println();
                        writer.flush();
                        System.out.println("What is the caption for your post?");
                        String caption = s.nextLine();
                        writer.write(caption);
                        writer.println();
                        writer.flush();
                        System.out.println("What is the path for the image for your post?");
                        String path = s.nextLine();
                        writer.write(path);
                        writer.println();
                        writer.flush();
                        break;
                    case 8:
                        writer.write("Option 8");
                        writer.println();
                        writer.flush();
                        System.out.println("Enter the post number");
                        int postNumber = s.nextInt();
                        s.nextLine();
                        writer.write(postNumber);
                        writer.println();
                        writer.flush();
                        String postDeletionResult = reader.readLine();
                        System.out.println(postDeletionResult);
                        break;
                    case 9:
                        writer.write("Option 9");
                        writer.println();
                        writer.flush();
                        System.out.println("Enter the post number");
                        int postNumberEdit = s.nextInt();
                        s.nextLine();
                        writer.write(postNumberEdit);
                        writer.println();
                        writer.flush();
                        System.out.println("Enter a new caption");
                        String newCaption = s.nextLine();
                        writer.write(newCaption);
                        writer.println();
                        writer.flush();
                        String editPostResposne = reader.readLine();
                        System.out.println(editPostResposne);
                        break;
                    case 10:
                        writer.write("Option 10");
                        writer.println();
                        writer.flush();
                        System.out.println("Enter the post number");
                        int commentPostNumber = s.nextInt();
                        s.nextLine();
                        writer.write(commentPostNumber);
                        writer.println();
                        writer.flush();
                        System.out.println("Enter your comment");
                        String comment = s.nextLine();
                        writer.write(comment);
                        writer.println();
                        writer.flush();
                        String commentResult = reader.readLine();
                        System.out.println(commentResult);
                        break;
                    case 11:
                        writer.write("Option 11");
                        writer.println();
                        writer.flush();
                        System.out.println("Enter the post number");
                        int deleteCommentPost = s.nextInt();
                        s.nextLine();
                        writer.write(deleteCommentPost);
                        writer.println();
                        writer.flush();
                        System.out.println("Enter your comment number");
                        int commentNum = s.nextInt();
                        s.nextLine();
                        writer.write(commentNum);
                        writer.println();
                        writer.flush();
                        String deleteCommentResponse = reader.readLine();
                        System.out.println(deleteCommentResponse);
                        break;
                    case 12:
                        writer.write("Option 12");
                        writer.println();
                        writer.flush();
                        System.out.println("Enter the post number");
                        int editCommentPost = s.nextInt();
                        s.nextLine();
                        writer.write(editCommentPost);
                        writer.println();
                        writer.flush();
                        System.out.println("Enter your comment number");
                        int editCommentNum = s.nextInt();
                        s.nextLine();
                        writer.write(editCommentNum);
                        writer.println();
                        writer.flush();
                        System.out.println("Enter your new comment");
                        String newComment = s.nextLine();
                        writer.write(newComment);
                        writer.println();
                        writer.flush();
                        String editCommentResponse = reader.readLine();
                        System.out.println(editCommentResponse);
                        break;
                    case 13:
                        writer.write("Option 13");
                        writer.println();
                        writer.flush();
                        System.out.println("Enter the post number");
                        int upvotePost = s.nextInt();
                        s.nextLine();
                        writer.write(upvotePost);
                        writer.println();
                        writer.flush();
                        String upvoteResponse = reader.readLine();
                        System.out.println(upvoteResponse);
                        break;
                    case 14:
                        writer.write("Option 14");
                        writer.println();
                        writer.flush();
                        System.out.println("Enter the post number");
                        int downvotePost = s.nextInt();
                        s.nextLine();
                        writer.write(downvotePost);
                        writer.println();
                        writer.flush();
                        String downvoteResponse = reader.readLine();
                        System.out.println(downvoteResponse);
                        break;
                    case 15:
                        writer.write("Option 15");
                        writer.println();
                        writer.flush();
                        System.out.println("Enter your old password");
                        String oldPass = s.nextLine();
                        writer.write(oldPass);
                        writer.println();
                        writer.flush();
                        System.out.println("Enter your new password");
                        String newPass = s.nextLine();
                        writer.write(newPass);
                        writer.println();
                        writer.flush();
                        String changePassResult = reader.readLine();
                        System.out.println(changePassResult);
                        break;
                    case 16:
                        writer.write("Option 16");
                        writer.println();
                        writer.flush();
                        runLoop = false;
                        socket.close();
                        break;
                    default:
                        System.out.println("Please select a valid option");


                }
            }






        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Connection Failed", "Search Engine",
                    JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (writer != null) {
                    writer.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error closing resources", "Search Engine",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    public static void main(String[] args) {
        Thread thread = new Thread(new Client());
        thread.start();
    }
    private JPanel createWelcomePage() {
        // Use BorderLayout for the main panel
        JPanel panel = new JPanel(new BorderLayout());

        // Create the label
        JLabel welcomeLabel = new JLabel("Welcome to Twitter!");
        welcomeLabel.setFont(new Font("Roboto", Font.PLAIN, 70));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);  // Center the label horizontally

        // Create the buttons
        JButton loginButton = new JButton("Login");
        JButton signUpButton = new JButton("Sign Up");

        // Add action listeners to switch to the login or sign-up page
        loginButton.addActionListener(e -> cardLayout.show(cardPanel, "LoginPage"));
        signUpButton.addActionListener(e -> cardLayout.show(cardPanel, "SignUpPage"));

        // Create a panel to hold the buttons and use FlowLayout to center them horizontally
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        buttonPanel.add(loginButton);
        buttonPanel.add(signUpButton);

        // Add the welcome label to the center of the BorderLayout panel
        panel.add(welcomeLabel, BorderLayout.CENTER);

        // Add the button panel to the bottom of the BorderLayout panel
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createLoginPage() {
        JPanel panel = new JPanel();
        JTextField usernameField = new JTextField(10);
        JPasswordField passwordField = new JPasswordField(10);
        JButton loginButton = new JButton("Login");

        // Add action listener for login
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            sendLoginRequest(username, password);
        });

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(loginButton);

        return panel;
    }

    // Method to create the Sign Up Page
    private JPanel createSignUpPage() {
        JPanel panel = new JPanel(new BorderLayout());

        // Create text fields with 10 columns
        JTextField firstNameField = new JTextField(5);
        JTextField lastNameField = new JTextField(5);
        JTextField userField = new JTextField(5);
        JPasswordField passField = new JPasswordField(5);

        // Create a sub-panel for the text fields and labels (input panel)
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add labels and text fields to the input panel
        inputPanel.add(new JLabel("First Name:"));
        inputPanel.add(firstNameField);
        inputPanel.add(new JLabel("Last Name:"));
        inputPanel.add(lastNameField);
        inputPanel.add(new JLabel("Username:"));
        inputPanel.add(userField);
        inputPanel.add(new JLabel("Password:"));
        inputPanel.add(passField);

        // Create sign-up button and add to the inputPanel
        JButton signUpButton = new JButton("Sign Up");
        inputPanel.add(signUpButton);  // Add the button after the fields

        // Create image label and upload button
        JLabel imageLabel = new JLabel("No Image Selected", SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(200, 200)); // Set fixed size for the label
        JButton uploadButton = new JButton("Upload Profile Picture");

        // Action listener for image upload
        uploadButton.addActionListener(e -> {
            String path = promptForProfilePicture();
            ImageIcon profileImageIcon = new ImageIcon(path);

            if (profileImageIcon != null) {
                // Resize the image to fit the label
                Image img = profileImageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                profileImageIcon = new ImageIcon(img);
                imageLabel.setIcon(profileImageIcon);
                imageLabel.setText(""); // Remove "No Image Selected" text
                pfpPath = path; // Store the profile picture path
            } else {
                JOptionPane.showMessageDialog(panel, "No image was selected.");
            }
        });

        // Action listener for sign-up
        signUpButton.addActionListener(e -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String username = userField.getText();
            String password = new String(passField.getPassword());
            sendSignUpRequest(firstName, lastName, username, password, pfpPath);
            cardPanel.add(createMainPage(), "MainPage");
            cardLayout.show(cardPanel, "MainPage");
        });

        // Create a panel for the image label and upload button
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BorderLayout());
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        imagePanel.add(uploadButton, BorderLayout.SOUTH);

        // Add image panel to the NORTH region
        panel.add(imagePanel, BorderLayout.NORTH);

        // Add the input panel (containing text fields and labels) to the center region
        panel.add(inputPanel, BorderLayout.SOUTH);

        return panel;
    }






    private JPanel createMainPage() {
        String hostName = "localhost";
        int portNumber = 4242;
        List<Post> posts = new ArrayList<>();
        try (Socket socket = new Socket(hostName, portNumber);
             PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            /**pw.println("Option 6");

            String line;
            String comment;


            while (((line = br.readLine()) != null) && !((line = br.readLine()).equals("stop"))) {
                if (line.equals("There are no posts in your feed.")) {
                    break;
                }
                if (line.equals("------------")) {
                    String username = br.readLine().substring(9).trim();
                    String caption = br.readLine().substring(8).trim();
                    String image = br.readLine().substring(6).trim();
                    int upvote = Integer.parseInt(br.readLine().substring(8).trim());
                    int downvote = Integer.parseInt(br.readLine().substring(10).trim());
                    ArrayList<String> comments = new ArrayList<String>();
                    while (((comment = br.readLine()) != null) && !((comment = br.readLine()).equals("------------"))) {
                        if (comment.equals("None")) {
                            break;
                        }
                        comments.add(comment);
                    }
                    Post post = new Post(username, caption, image, upvote, downvote, comments);
                    posts.add(post);

                }

            }
             */


        } catch (IOException e) {
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(null, "Connection error", "Error", JOptionPane.ERROR_MESSAGE);
            });
        }
        // Use BorderLayout for the main panel
        JPanel panel = new JPanel(new BorderLayout());
        // Create the page label
        JLabel pageLabel = new JLabel("Main Page", SwingConstants.CENTER);
        pageLabel.setFont(new Font("Roboto", Font.BOLD, 50));

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
            sendExitMessage();
        });

        // Create the feed panel to hold all posts
        JPanel feedPanel = new JPanel();
        feedPanel.setLayout(new BoxLayout(feedPanel, BoxLayout.Y_AXIS));  // Stack posts vertically



        // Sort the posts based on total votes (upvotes - downvotes)
        posts.sort((p1, p2) -> Integer.compare(p2.getTotalVotes(), p1.getTotalVotes()));  // Sort in descending order

        // Loop through the sorted posts and create a panel for each one
        for (Post post : posts) {
            // Create a panel for this post
            JPanel postPanel = new JPanel();
            postPanel.setLayout(new BorderLayout());

            // Load the image and create an ImageIcon
            ImageIcon imageIcon = new ImageIcon(post.getImage());  // Path to image (ensure image exists)
            JLabel imageLabel = new JLabel(imageIcon);

            // Create a label for the caption
            JLabel captionLabel = new JLabel(post.getCaption());
            captionLabel.setFont(new Font("Roboto", Font.PLAIN, 16));

            // Create a label for the votes
            JLabel voteLabel = new JLabel("Upvotes: " + post.getUpvote() + " | Downvotes: " + post.getDownvote());
            voteLabel.setFont(new Font("Roboto", Font.PLAIN, 14));

            // Create upvote and downvote buttons
            JButton upvoteButton = new JButton("Upvote");
            JButton downvoteButton = new JButton("Downvote");

            // Add action listeners to the buttons to handle votes
            upvoteButton.addActionListener(e -> {
                post.incrementUpvote();
                updateFeed(feedPanel, posts);
            });

            downvoteButton.addActionListener(e -> {
                post.incrementDownvote();
                updateFeed(feedPanel, posts);
            });

            // Create a panel for the buttons (upvote and downvote)
            JPanel votePanel = new JPanel();
            votePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            votePanel.add(upvoteButton);
            votePanel.add(downvoteButton);

            // Add the image, caption, votes, and vote buttons to the post panel
            postPanel.add(imageLabel, BorderLayout.NORTH);
            postPanel.add(captionLabel, BorderLayout.CENTER);
            postPanel.add(voteLabel, BorderLayout.SOUTH);
            postPanel.add(votePanel, BorderLayout.SOUTH);

            // Add the post panel to the feed
            feedPanel.add(postPanel);
        }

        // Make the feed scrollable
        JScrollPane scrollPane = new JScrollPane(feedPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBackground(Color.LIGHT_GRAY);
        sidePanel.setBorder(BorderFactory.createTitledBorder("Actions"));
        JButton addFriend = new JButton("Add Friend");
        sidePanel.add(addFriend);
        addFriend.addActionListener(e -> {
            // action
        });
        JButton blockUser = new JButton("Block a User");
        sidePanel.add(blockUser);
        blockUser.addActionListener(e -> {
            // action
        });
        JButton unBlockUser = new JButton("Unblock a User");
        sidePanel.add(unBlockUser);
        unBlockUser.addActionListener(e -> {
            // action
        });
        JButton viewAUserProfile = new JButton("View a User Profile");
        sidePanel.add(viewAUserProfile);
        viewAUserProfile.addActionListener(e -> {
            // action
        });
        JButton createPost = new JButton("Create a Post");
        sidePanel.add(createPost);
        createPost.addActionListener(e -> {
            // action
        });
        JButton deletePost = new JButton("Delete a Post");
        sidePanel.add(deletePost);
        deletePost.addActionListener(e -> {
            // action
        });
        JButton editPost = new JButton("Edit a Post");
        sidePanel.add(editPost);
        editPost.addActionListener(e -> {
            // action
        });
        JButton createAComment = new JButton("Create a Comment");
        sidePanel.add(createAComment);
        createAComment.addActionListener(e -> {
            // action
        });
        JButton deleteAComment = new JButton("Delete a Comment");
        sidePanel.add(deleteAComment);
        deleteAComment.addActionListener(e -> {
            // action
        });
        JButton editComment = new JButton("Edit a Comment");
        sidePanel.add(editComment);
       editComment.addActionListener(e -> {
            // action
        });



        // Add components to the main panel
        panel.add(pageLabel, BorderLayout.NORTH);  // Page title at the top
        panel.add(scrollPane, BorderLayout.CENTER);// Feed in the center
        panel.add(exitButton, BorderLayout.SOUTH);
        panel.add(sidePanel, BorderLayout.WEST);
        return panel;

    }



    // Method to update the feed after voting
    private void updateFeed(JPanel feedPanel, List<Post> posts) {
        // Clear the current feed
        feedPanel.removeAll();

        // Re-sort the posts based on updated votes
        posts.sort((p1, p2) -> Integer.compare(p2.getTotalVotes(), p1.getTotalVotes()));

        // Rebuild the feed with updated posts
        for (Post post : posts) {
            JPanel postPanel = new JPanel();
            postPanel.setLayout(new BorderLayout());

            ImageIcon imageIcon = new ImageIcon(post.getImage());  // Path to image (ensure image exists)
            JLabel imageLabel = new JLabel(imageIcon);

            JLabel captionLabel = new JLabel(post.getCaption());
            captionLabel.setFont(new Font("Roboto", Font.PLAIN, 16));

            JLabel voteLabel = new JLabel("Upvotes: " + post.getUpvote() + " | Downvotes: " + post.getDownvote());
            voteLabel.setFont(new Font("Roboto", Font.PLAIN, 14));

            JButton upvoteButton = new JButton("Upvote");
            JButton downvoteButton = new JButton("Downvote");

            upvoteButton.addActionListener(e -> {
                post.incrementUpvote();
                updateFeed(feedPanel, posts);
            });

            downvoteButton.addActionListener(e -> {
                post.incrementDownvote();
                updateFeed(feedPanel, posts);
            });

            JPanel votePanel = new JPanel();
            votePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            votePanel.add(upvoteButton);
            votePanel.add(downvoteButton);

            postPanel.add(imageLabel, BorderLayout.NORTH);
            postPanel.add(captionLabel, BorderLayout.CENTER);
            postPanel.add(voteLabel, BorderLayout.SOUTH);
            postPanel.add(votePanel, BorderLayout.SOUTH);

            feedPanel.add(postPanel);
        }

        // Refresh the feed
        feedPanel.revalidate();
        feedPanel.repaint();
    }




    // Method to prompt for a profile picture
    private String promptForProfilePicture() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Profile Picture");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif"));

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                return selectedFile.getAbsolutePath();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error loading image: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }
        return null;
    }
    private void connectToServer() {
        String hostName = "localhost";
        int portNumber = 4242;
        try (Socket socket = new Socket(hostName, portNumber);
             PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

        } catch (IOException e) {
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(null, "Can't Connect to Server", "Error", JOptionPane.ERROR_MESSAGE);
            });
        }

    }
    private void sendLoginRequest(String username, String password) {
        String hostName = "localhost";
        int portNumber = 4242;
        try (Socket socket = new Socket(hostName, portNumber);
             PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Send login request
            pw.println("Login");
            pw.println(username);
            pw.println(password);

            // Wait for server response
            String response = br.readLine();
            SwingUtilities.invokeLater(() -> {
                if ("true".equals(response)) {
                    JOptionPane.showMessageDialog(null, "Login successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                    cardPanel.add(createMainPage(), "MainPage");
                    cardLayout.show(cardPanel, "MainPage");
                } else {
                    JOptionPane.showMessageDialog(null, "Login failed", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

        } catch (IOException e) {
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(null, "Connection error", "Error", JOptionPane.ERROR_MESSAGE);
            });
        }
    }
    private void sendSignUpRequest(String first, String last, String username, String password, String path) {
        String hostName = "localhost";
        int portNumber = 4242;
        try (Socket socket = new Socket(hostName, portNumber);
             PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Send login request
            pw.println("Sign Up");
            pw.println(first);
            pw.println(last);
            pw.println(username);
            String userResponse = br.readLine();
            SwingUtilities.invokeLater(() -> {
                if ("false".equals(userResponse)) {
                    JOptionPane.showMessageDialog(null, "Username Invalid", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            pw.println(password);
            String passResponse = br.readLine();
            SwingUtilities.invokeLater(() -> {
                if ("false".equals(passResponse)) {
                    JOptionPane.showMessageDialog(null, "Password Invalid", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Account Successfully Created", "Twitter", JOptionPane.INFORMATION_MESSAGE);
                }
            });
            pw.println(path);




        } catch (IOException e) {
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(null, "Connection error", "Error", JOptionPane.ERROR_MESSAGE);
            });
        }
    }

    private void sendExitMessage() {
        String hostName = "localhost";
        int portNumber = 4242;
        try (Socket socket = new Socket(hostName, portNumber);
             PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Send login request
            pw.println("Option 16");
            System.exit(0);
        } catch (IOException e) {
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(null, "Connection error", "Error", JOptionPane.ERROR_MESSAGE);
            });
        }

    }



}









