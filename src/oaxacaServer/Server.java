package oaxacaServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public final class Server implements Runnable {

	private static Server instance = null;
	private int port;
	private Set<String> username = new HashSet<>();
	private Set<UserThread> userThreads = new HashSet<>();
	private final List<Listener> listeners = new ArrayList<>();
	private Queue<String> queue = new LinkedList<>();;
	boolean running = false;

	private Server() {
	}

	public static Server getInstance() {
		if (instance == null) {
			instance = new Server();
		}
		return instance;
	}

	public void setPort(int port) {
		this.port = port;
	}

	@Override
	public void run() {
		if(running) {
			listeners.remove(listeners.size()-1);
			write("Server already started");
			return;
		}
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			running = true;
			write("Server started on port " + port);
			while (running) {
				Socket socket = serverSocket.accept();
				UserThread newUser = new UserThread(socket);
				userThreads.add(newUser);
				newUser.start();
			}
			serverSocket.close();
		} catch (IOException e) {
			write("Port " + port + " is already used");
			listeners.remove(listeners.size() - 1);
		}finally {
			running = false;
		}
	}

	public void write(String string) {
		queue.add("[" + java.time.LocalTime.now() + "] " + string);
		for (Listener listener : listeners) {
			listener.onListChange();
		}
	}

	public void addUserName(String string) {
		username.add(string);
	}

	public void removeUser(String string, UserThread user) {
		username.remove(string);
		userThreads.remove(user);
	}

	public void addListener(Listener listener) {
		listeners.add(listener);
	}

	public Queue getQueue() {
		return queue;
	}

	public void close() {
		write("Server on port " + port + " closed.");
		running = false;
		listeners.clear();
	}

}
