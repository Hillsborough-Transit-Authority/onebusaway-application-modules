/**
 * Copyright (C) 2011 Brian Ferris <bdferris@onebusaway.org>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.onebusaway.transit_data_federation_webapp.controllers;

import java.util.List;

import org.onebusaway.gtfs.model.AgencyAndId;
import org.onebusaway.transit_data_federation.services.AgencyAndIdLibrary;
import org.onebusaway.transit_data_federation.services.transit_graph.StopEntry;
import org.onebusaway.transit_data_federation.services.transit_graph.TransitGraphDao;
import org.onebusaway.transit_data_federation.services.tripplanner.StopTransfer;
import org.onebusaway.transit_data_federation.services.tripplanner.StopTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/transfers-for-stop.action")
public class TransfersForStopController {

  @Autowired
  private TransitGraphDao _graphDao;
  
  @Autowired
  private StopTransferService _stopTransferService;

  @RequestMapping()
  public ModelAndView index(@RequestParam() String stopId) {

    AgencyAndId id = AgencyAndIdLibrary.convertFromString(stopId);
    
    StopEntry stop = _graphDao.getStopEntryForId(id);
    List<StopTransfer> transfers = _stopTransferService.getTransfersFromStop(stop);

    ModelAndView mv = new ModelAndView("transfers-for-stop.jspx");
    mv.addObject("transfers", transfers);
    return mv;
  }
}
