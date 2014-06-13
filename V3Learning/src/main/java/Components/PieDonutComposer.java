package Components;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.QueryParam;
import org.zkoss.chart.Charts;
import org.zkoss.chart.Color;
import org.zkoss.chart.Point;
import org.zkoss.chart.Series;
import org.zkoss.chart.plotOptions.DataLabels;
import org.zkoss.chart.plotOptions.PiePlotOptions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;

import Model.DBConnection;
import Model.DBOperations;
import Model.Skill;
import Model.User;

public class PieDonutComposer extends SelectorComposer<Window> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1429706169065142220L;
	
	private Map<String, Map<String, Double>> domains;
	
	@Wire
    Charts chart;

	
	
    public void doAfterCompose(Window comp) throws Exception {
    	
    	System.out.println("after compose");
    	int usersIds = 1;
		User visitedUser = null;
		DBOperations dbo = (DBOperations) Sessions.getCurrent().getAttribute(
				"dbOperations");
		if(dbo ==null){
			DBConnection dbc = new DBConnection();
			dbc.connectToDB();
			dbo = new DBOperations(dbc);
		}
		for (User us : dbo.getAllUsers()) {
			if(us.getId()==usersIds)
			{
				visitedUser = us;
				visitedUser.computeUserLists();
				return;
			}
		}
		domains = new LinkedHashMap<String, Map<String, Double>>();
		Map<String, Double> skills;
		
		System.out.println(visitedUser.getEmail());
		
		for(Skill skill : visitedUser.getSkills()){
			domains.put(skill.getDomainName(), new LinkedHashMap<String, Double>());
		}
		
		for(Skill skill : visitedUser.getSkills()){
			skills = domains.get(skill.getDomainName());
			skills.put(skill.getName(), 1.0/visitedUser.getSkills().size());
			domains.put(skill.getDomainName(), skills);
		}
        super.doAfterCompose(comp);

        chart.getYAxis().setTitle("Total percent market share");
        chart.getPlotOptions().getPie().setShadow(false);
        chart.getPlotOptions().getPie().setCenter("50%", "50%");
        chart.getTooltip().setValueSuffix("%");
        initSeries();
    }
    
    private void initSeries() {
        Series browser = chart.getSeries();
        Series version = chart.getSeries(1);
        browser.setName("Browsers");
        List<Color> colors = chart.getColors();
        int i = 0;
        for (Entry<String, Map<String, Double>> browserEntry: domains.entrySet()) {
            String key = browserEntry.getKey();
            double browserValue = browserEntry.hashCode();
            Point browserPoint = new Point(key, browserValue);
            browserPoint.setColor(colors.get(i));
            if (browserValue < 5)
                browserPoint.getDataLabels().setEnabled(false);
            browser.addPoint(browserPoint);
            for (Map.Entry<String, Double> versionEntry: browserEntry.getValue().entrySet()) {
                double versionValue = versionEntry.getValue();
                Point versionPoint = new Point(versionEntry.getKey(), versionValue);
                versionPoint.setColor(colors.get(i));
                if (versionValue < 1) {
                    versionPoint.getDataLabels().setEnabled(false);
                }
                version.addPoint(versionPoint);
            }
            i += 1;
        }
        PiePlotOptions browserPlotOptions = new PiePlotOptions();
        browserPlotOptions.setSize("60%");
        browser.setPlotOptions(browserPlotOptions);
        DataLabels browserLabels = browser.getDataLabels();
        browserLabels.setFormat("{point.name}");
        browserLabels.setColor("white");
        browserLabels.setDistance(-30);
        PiePlotOptions versionPlotOptions = new PiePlotOptions();
        versionPlotOptions.setSize("80%");
        versionPlotOptions.setInnerSize("60%");
        version.setPlotOptions(versionPlotOptions);
        version.getDataLabels().setFormat("<b>{point.name}:</b> {point.y}%");
    }
}