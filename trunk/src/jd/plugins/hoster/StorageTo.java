//    jDownloader - Downloadmanager
//    Copyright (C) 2009  JD-Team support@jdownloader.org
//
//    This program is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    This program is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with this program.  If not, see <http://www.gnu.org/licenses/>.

package jd.plugins.hoster;

import jd.PluginWrapper;
import jd.parser.Regex;
import jd.plugins.DownloadLink;
import jd.plugins.HostPlugin;
import jd.plugins.LinkStatus;
import jd.plugins.PluginException;
import jd.plugins.PluginForHost;
import jd.plugins.DownloadLink.AvailableStatus;

@HostPlugin(revision = "$Revision$", interfaceVersion = 2, names = { "storage.to" }, urls = { "http://[\\w\\.]*?storage.to/get/[a-zA-Z0-9]+/[a-zA-Z0-9-_.]+" }, flags = { 0 })
public class StorageTo extends PluginForHost {

    public StorageTo(PluginWrapper wrapper) {
        super(wrapper);
        //premium http://www.storage.to/affiliate/Slh9BLxH
    }

    @Override
    public String getAGBLink() {
        return "http://www.storage.to/tos";
    }

    @Override
    public void handleFree(DownloadLink link) throws Exception {
        requestFileInformation(link);
        br.setFollowRedirects(false);
        String infolink = link.getDownloadURL().replace("http://www.storage.to/get", "http://www.storage.to/getlink");
        br.getPage(infolink);
        if (br.getRegex("'state' : '(.*?)'").getMatch(0).equals("wait")) {
            int wait = Integer.valueOf(br.getRegex("'countdown' : (.*?),").getMatch(0)).intValue();
            throw new PluginException(LinkStatus.ERROR_IP_BLOCKED, 60 * 60 * wait);
        }
        String time = br.getRegex("'countdown' : (.*?),").getMatch(0);
        long sleeptime = 0;
        try {
            sleeptime = Long.parseLong(time);
        } catch (Exception e) {
        }
        if (sleeptime > 0) sleep((sleeptime + 1) * 1000, link);
        String dllink;
        dllink = br.getRegex("'link' : '(.*?)',").getMatch(0);
        if (dllink == null) throw new PluginException(LinkStatus.ERROR_PLUGIN_DEFEKT);
        link.setFinalFileName(null);
        dl = br.openDownload(link, dllink, false, 1);
        dl.startDownload();

    }

    @Override
    public AvailableStatus requestFileInformation(DownloadLink parameter) throws Exception {
        this.setBrowserExclusive();
        br.clearCookies("storage.to");
        br.getPage(parameter.getDownloadURL());
        if (br.containsHTML("File not found.")) throw new PluginException(LinkStatus.ERROR_FILE_NOT_FOUND);
        String filename = br.getRegex("<span class=\"orange\">Downloading:</span>(.*?)<span class=\"light\">(.*?)</span>").getMatch(0);
        String filesize = br.getRegex("<span class=\"orange\">Downloading:</span>(.*?)<span class=\"light\">(.*?)</span>").getMatch(1);
        if (filename == null || filesize == null) throw new PluginException(LinkStatus.ERROR_FILE_NOT_FOUND);
        parameter.setName(filename.trim());
        parameter.setDownloadSize(Regex.getSize(filesize.replaceAll(",", "\\.")));
        return AvailableStatus.TRUE;
    }

    @Override
    public void reset() {
    }

    @Override
    public void resetDownloadlink(DownloadLink link) {
    }

    @Override
    /*
     * public String getVersion() { return getVersion("$Revision$"); }
     */
    public int getMaxSimultanFreeDownloadNum() {
        return 1;
    }

}
