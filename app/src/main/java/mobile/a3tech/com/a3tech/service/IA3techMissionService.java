package mobile.a3tech.com.a3tech.service;

import java.util.List;

import mobile.a3tech.com.a3tech.model.A3techMission;

public interface IA3techMissionService {

    public String createMission(String mission);
    public List<A3techMission> filtreMission(String lang, String connectedUser, String keyWord, String distance, String services, String start, String limit, String key, String typeTransaction, String premium, String password,
                                             int order, int type);
    String deleteMission(String identifiant,  String idUser,String password);
}
