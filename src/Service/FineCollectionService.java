package Service;

import java.sql.ResultSet;

import dao.FineCollectionDAO;

public class FineCollectionService {
	private FineCollectionDAO fDAO;

	public FineCollectionService() {
		super();
		fDAO = new FineCollectionDAO();
	}
	public ResultSet getTotalFineInTime() { 
		return FineCollectionDAO.getTotalFineInTime();
	}
	
}
