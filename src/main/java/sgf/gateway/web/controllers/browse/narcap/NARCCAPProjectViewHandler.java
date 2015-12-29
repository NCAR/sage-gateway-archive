package sgf.gateway.web.controllers.browse.narcap;

import org.springframework.web.servlet.ModelAndView;
import sgf.gateway.dao.metadata.DatasetRepository;
import sgf.gateway.model.metadata.activities.project.Project;
import sgf.gateway.utils.time.DateStrategy;
import sgf.gateway.web.controllers.browse.DefaultProjectViewHandler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @author wilhelmi
 */
public class NARCCAPProjectViewHandler extends DefaultProjectViewHandler {

    private final DatasetRepository datasetRepository;
    private final DateStrategy dateStrategy;

    public NARCCAPProjectViewHandler(DatasetRepository datasetRepository, DateStrategy dateStrategy) {

        this.datasetRepository = datasetRepository;
        this.dateStrategy = dateStrategy;
    }

    public boolean supports(Project project) {

        boolean value = false;

        if (project.getName().equals("NARCCAP")) {

            value = true;
        }

        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelAndView handleProjectView(Project project) {

        ModelAndView modelAndView = new ModelAndView(this.getViewName());

        Map<String, Object> datasetMap = new HashMap<>();
        datasetMap.put("project", project);

        datasetMap.put("crcmCGM3", this.datasetRepository.getByShortName("narccap.crcm.output.cgm3"));
        datasetMap.put("crcmCCSM", this.datasetRepository.getByShortName("narccap.crcm.output.ccsm"));
        datasetMap.put("crcmNCEP", this.datasetRepository.getByShortName("narccap.crcm.output.ncep"));

        datasetMap.put("ecp2GFDL", this.datasetRepository.getByShortName("narccap.ecp2.output.gfdl"));
        datasetMap.put("ecp2HADCM3", this.datasetRepository.getByShortName("narccap.ecp2.output.hadcm3"));
        datasetMap.put("ecp2NCEP", this.datasetRepository.getByShortName("narccap.ecp2.output.ncep"));

        datasetMap.put("hrm3GFDL", this.datasetRepository.getByShortName("narccap.hrm3.output.gfdl"));
        datasetMap.put("hrm3HADCM3", this.datasetRepository.getByShortName("narccap.hrm3.output.hadcm3"));
        datasetMap.put("hrm3NCEP", this.datasetRepository.getByShortName("narccap.hrm3.output.ncep"));

        datasetMap.put("mm5iHADCM3", this.datasetRepository.getByShortName("narccap.mm5i.output.hadcm3"));
        datasetMap.put("mm5iCCSM", this.datasetRepository.getByShortName("narccap.mm5i.output.ccsm"));
        datasetMap.put("mm5iNCEP", this.datasetRepository.getByShortName("narccap.mm5i.output.ncep"));

        datasetMap.put("rcm3GFDL", this.datasetRepository.getByShortName("narccap.rcm3.output.gfdl"));
        datasetMap.put("rcm3CGM3", this.datasetRepository.getByShortName("narccap.rcm3.output.cgm3"));
        datasetMap.put("rcm3NCEP", this.datasetRepository.getByShortName("narccap.rcm3.output.ncep"));

        datasetMap.put("wrfgCGM3", this.datasetRepository.getByShortName("narccap.wrfg.output.cgm3"));
        datasetMap.put("wrfgCCSM", this.datasetRepository.getByShortName("narccap.wrfg.output.ccsm"));
        datasetMap.put("wrfgNCEP", this.datasetRepository.getByShortName("narccap.wrfg.output.ncep"));

        datasetMap.put("tsGFDL", this.datasetRepository.getByShortName("narccap.time_slices.output.gfdl"));
        datasetMap.put("tsCCSM", this.datasetRepository.getByShortName("narccap.time_slices.output.ccsm"));

        datasetMap.put("ecpcGFDL", this.datasetRepository.getByShortName("narccap.ecpc.output.gfdl"));
        datasetMap.put("ecpcHADCM3", this.datasetRepository.getByShortName("narccap.ecpc.output.hadcm3"));
        datasetMap.put("ecpcNCEP", this.datasetRepository.getByShortName("narccap.ecpc.output.ncep"));

        datasetMap.put("wrfpCGM3", this.datasetRepository.getByShortName("narccap.wrfp.output.cgm3"));
        datasetMap.put("wrfpCCSM", this.datasetRepository.getByShortName("narccap.wrfp.output.ccsm"));
        datasetMap.put("wrfpNCEP", this.datasetRepository.getByShortName("narccap.wrfp.output.ncep"));

        datasetMap.put("hadcm3BoundaryConditions", this.datasetRepository.getByShortName("narccap.hadcm3.output"));

        modelAndView.addObject("datasetMap", datasetMap);

        Date date = dateStrategy.getDate();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String currentDate = simpleDateFormat.format(date);

        modelAndView.addObject("currentDate", currentDate);

        return modelAndView;
    }
}
