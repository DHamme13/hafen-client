/*
 *  This file is part of the Haven & Hearth game client.
 *  Copyright (C) 2009 Fredrik Tolf <fredrik@dolda2000.com>, and
 *                     Björn Johannessen <johannessen.bjorn@gmail.com>
 *
 *  Redistribution and/or modification of this file is subject to the
 *  terms of the GNU Lesser General Public License, version 3, as
 *  published by the Free Software Foundation.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  Other parts of this source tree adhere to other copying
 *  rights. Please see the file `COPYING' in the root directory of the
 *  source tree for details.
 *
 *  A copy the GNU Lesser General Public License is distributed along
 *  with the source tree of which this file is a part in the file
 *  `doc/LPGL-3'. If it is missing for any reason, please see the Free
 *  Software Foundation's website at <http://www.fsf.org/>, or write
 *  to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 *  Boston, MA 02111-1307 USA
 */

package haven;

import java.util.*;
import java.awt.event.KeyEvent;

public class Fightsess extends Widget {
    public static final int actpitch = 50;
    public final Indir<Resource>[] actions;
    public Coord pcc;

    @RName("fsess")
    public static class $_ implements Factory {
	public Widget create(Coord c, Widget parent, Object[] args) {
	    int nact = (Integer)args[0];
	    return(new Fightsess(parent, nact));
	}
    }

    @SuppressWarnings("unchecked")
    public Fightsess(Widget parent, int nact) {
	super(Coord.z, parent.sz, parent);
	pcc = sz.div(2);
	this.actions = (Indir<Resource>[])new Indir[nact];
    }

    private void updatepos() {
	MapView map;
	Gob pl;
	if(((map = getparent(GameUI.class).map) == null) || ((pl = map.player()) == null) || (pl.sc == null))
	    return;
	pcc = pl.sc;
    }

    public void draw(GOut g) {
	updatepos();
	Coord ca = pcc.add(-(actions.length * actpitch) / 2, 25);
	for(Indir<Resource> act : actions) {
	    try {
		if(act != null) {
		    Tex img = act.get().layer(Resource.imgc).tex();
		    g.image(img, ca);
		}
	    } catch(Loading l) {}
	    ca.x += actpitch;
	}
    }

    public void uimsg(String msg, Object... args) {
	if(msg == "act") {
	    int n = (Integer)args[0];
	    Indir<Resource> res = (args.length > 1)?ui.sess.getres((Integer)args[1]):null;
	    actions[n] = res;
	} else {
	    super.uimsg(msg, args);
	}
    }

    public boolean globtype(char key, KeyEvent ev) {
	int c = ev.getKeyChar();
	if((key == 0) && (c >= KeyEvent.VK_1) && (key < KeyEvent.VK_1 + actions.length)) {
	    wdgmsg("use", c - KeyEvent.VK_1);
	    return(true);
	}
	return(super.globtype(key, ev));
    }
}
