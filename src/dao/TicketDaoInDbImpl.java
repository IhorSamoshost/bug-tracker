package dao;

import java.sql.Connection;

public class TicketDaoInDbImpl implements TicketDao {
    private Connection conn;

    public TicketDaoInDbImpl(Connection conn) {
        this.conn = conn;
    }
}
