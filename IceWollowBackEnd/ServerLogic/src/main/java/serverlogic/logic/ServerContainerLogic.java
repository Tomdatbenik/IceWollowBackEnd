package serverlogic.logic;

import serverlogic.factories.ServerFactory;
import serverlogic.interfaces.IServerContainerRepo;
import serverlogic.models.*;
import userlogic.models.User;

import java.util.List;

public class ServerContainerLogic {

    private ServerFactory serverFactory;
    private IServerContainerRepo repo;

    public ServerContainerLogic() {
        serverFactory = ServerFactory.getInstance();
        repo = serverFactory.getServerContainerRepo();
    }

    public List<IWServer> getAllServersByUser(User user)
    {
        return repo.getAllServersByUser(user);
    }

    public IWServer addServer(IWServer server)
    {
        //Add 2 channels for general chat at the start of a server creation.
        server.getTextChannels().add(new TextChannel("General"));
        server.getVoiceChannels().add(new VoiceChannel("General"));
        server.getUsers().add(server.getOwner());
        return repo.addServer(server);
    }

    public IWServer getServerById(int id)
    {
        return repo.getServerById(id);
    }
}