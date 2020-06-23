package dao;

import java.util.List;

import beans.Analyse;
import beans.AnalyseDevice;

public interface AnalyseDao {
	public void addAnalyse(Analyse a);
	public void deleteAnalyse(int id);
	public Analyse getAnalyse(int id);
	public List<Analyse> getAnalyse();
	public int getCountAnalyse();
	public List<AnalyseDevice> getAnalyses();
}
