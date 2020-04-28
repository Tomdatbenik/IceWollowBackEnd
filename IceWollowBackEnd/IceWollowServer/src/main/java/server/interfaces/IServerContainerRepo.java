package server.interfaces;

import server.models.IWServer;
import user.models.User;

import java.util.List;

public interface IServerContainerRepo {

    IWServer addServer(IWServer server);

    List<IWServer> getAllServersByUser(User user);

    IWServer getServerById(int id);
}
