package trs.statistic;

import trs.entity.enums.ActionEvent;

import java.time.LocalDateTime;

public interface Observer {

    void onEvent(LocalDateTime timestamp, ActionEvent event, String id, String addInfo);
}
