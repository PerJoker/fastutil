/*		 
 * Copyright (C) 2002 Sebastiano Vigna 
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation; either version 2, or (at your option) any
 * later version.
 *	
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *	
 * You should have received a copy of the GNU General Public License along
 * with this program; see the file COPYING.  If not, write to the Free
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 *
 */

package it.unimi.dsi.fastUtil;

/** Basic data for all hash-based classes.
 */

public interface Hash {

	 /** The initial default size of a hash table. */
	 final int DEFAULT_INITIAL_SIZE = 16;
	 /** The default load factor of a hash table. */
	 final float DEFAULT_LOAD_FACTOR = .75f;
	 /** The state of a free hash table entry. */
	 final byte FREE = 0;
	 /** The state of a busy hash table6 entry. */
	 final byte BUSY = -1;
	 /** The state of a hash table entry freed by a deletion. */
	 final byte REMOVED = 1;
	 
	 /** A list of primes to be used as table sizes. The <var>i</var>-th element is 
	  *  the largest prime smaller than 2<sup>(<var>i</var>+19)/16</sup>. */
	 int primes[] =     { 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 5, 5, 5, 5, 5, 5, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 11, 11, 11, 13, 13, 13, 13, 13, 13, 17, 17, 17, 19, 19, 19, 19, 23, 23, 23, 23, 23, 29, 31, 31, 31, 31, 37, 37, 37, 41, 43, 43, 47, 47, 47, 53, 53, 59, 61, 61, 67, 71, 73, 73, 79, 83, 89, 89, 97, 101, 103, 109, 113, 113, 127, 131, 137, 139, 151, 157, 163, 167, 179, 181, 193, 199, 211, 223, 233, 241, 251, 263, 277, 283, 293, 313, 317, 337, 359, 373, 389, 409, 421, 443, 467, 487, 509, 523, 557, 577, 607, 631, 661, 691, 719, 751, 787, 823, 859, 887, 937, 977, 1021, 1063, 1109, 1163, 1213, 1259, 1321, 1381, 1447, 1511, 1571, 1637, 1721, 1789, 1877, 1951, 2039, 2137, 2221, 2311, 2423, 2539, 2647, 2767, 2887, 3023, 3137, 3271, 3433, 3593, 3739, 3919, 4093, 4273, 4463, 4663, 4861, 5081, 5309, 5531, 5791, 6047, 6311, 6581, 6883, 7187, 7507, 7841, 8191, 8543, 8929, 9323, 9739, 10169, 10613, 11093, 11579, 12097, 12619, 13187, 13763, 14369, 15017, 15683, 16381, 17107, 17863, 18637, 19477, 20341, 21227, 22171, 23167, 24181, 25261, 26371, 27551, 28771, 30047, 31357, 32749, 34217, 35731, 37313, 38959, 40639, 42491, 44371, 46337, 48383, 50527, 52769, 55103, 57529, 60091, 62753, 65521, 68399, 71453, 74623, 77933, 81373, 84979, 88747, 92671, 96779, 101063, 105541, 110183, 115079, 120181, 125509, 131071, 136861, 142907, 149257, 155863, 162751, 169957, 177493, 185359, 193559, 202129, 211073, 220421, 230189, 240379, 251003, 262139, 273739, 285841, 298513, 311737, 325543, 339943, 355007, 370723, 387137, 404273, 422141, 440863, 460387, 480773, 502057, 524287, 547499, 571721, 597049, 623477, 651071, 679909, 710009, 741431, 774239, 808559, 844351, 881729, 920761, 961547, 1004117, 1048573, 1094999, 1143473, 1194103, 1246963, 1302181, 1359833, 1420037, 1482907, 1548553, 1617103, 1688681, 1763477, 1841557, 1923083, 2008231, 2097143, 2189989, 2286953, 2388187, 2493947, 2604347, 2719667, 2840077, 2965819, 3097123, 3234241, 3377431, 3526949, 3683117, 3846191, 4016477, 4194301, 4379987, 4573913, 4776403, 4987891, 5208727, 5439331, 5680153, 5931637, 6194219, 6468481, 6754879, 7053911, 7366223, 7692343, 8032943, 8388593, 8759977, 9147839, 9552847, 9975773, 10417441, 10878671, 11360311, 11863279, 12388511, 12936997, 13509757, 14107889, 14732503, 15384767, 16065911, 16777213, 17520001, 18295663, 19105679, 19951579, 20834903, 21757349, 22720631, 23726561, 24777019, 25873987, 27019541, 28215799, 29465003, 30769547, 32131829, 33554393, 35040001, 36591349, 38211389, 39903161, 41669821, 43514687, 45441229, 47453111, 49554061, 51748007, 54039079, 56431601, 58930033, 61539097, 64263659, 67108859, 70080019, 73182731, 76422809, 79806317, 83339657, 87029419, 90882541, 94906249, 99108043, 103495999, 108078151, 112863197, 117860081, 123078191, 128527319, 134217689, 140160011, 146365459, 152845549, 159612653, 166679257, 174058853, 181765097, 189812507, 198216203, 206992013, 216156341, 225726379, 235720171, 246156371, 257054669, 268435399, 280320101, 292730833, 305691193, 319225331, 333358657, 348117713, 363530177, 379625047, 396432493, 413984063, 432312689, 451452823, 471440329, 492312739, 514109311, 536870909, 560640167, 585461873, 611382467, 638450677, 666717329, 696235433, 727060381, 759250111, 792864991, 827968093, 864625397, 902905643, 942880691, 984625589, 1028218679, 1073741789, 1121280361, 1170923713, 1222764953, 1276901389, 1333434667, 1392470809, 1454120819, 1518500213, 1585729993, 1655936263, 1729250821, 1805811263, 1885761323, 1969251173, 2056437379, 2147483647 };
	 /** A list of primes to be used to compute secondary hashes. The <var>i</var>-th element is the
	  * largest prime smaller than the largest prime smaller than 2<sup>(<var>i</var>+19)/16</sup> (except for
	  * the first entries,  which are necessarily 1). */
	 int prevPrimes[] = { 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5,  7,  7,  7, 11, 11, 11, 11, 11, 11, 13, 13, 13, 17, 17, 17, 17, 19, 19, 19, 19, 19, 23, 29, 29, 29, 29, 31, 31, 31, 37, 41, 41, 43, 43, 43, 47, 47, 53, 59, 59, 61, 67, 71, 71, 73, 79, 83, 83, 89,  97, 101, 107, 109, 109, 113, 127, 131, 137, 149, 151, 157, 163, 173, 179, 191, 197, 199, 211, 229, 239, 241, 257, 271, 281, 283, 311, 313, 331, 353, 367, 383, 401, 419, 439, 463, 479, 503, 521, 547, 571, 601, 619, 659, 683, 709, 743, 773, 821, 857, 883, 929, 971, 1019, 1061, 1103, 1153, 1201, 1249, 1319, 1373, 1439, 1499, 1567, 1627, 1709, 1787, 1873, 1949, 2029, 2131, 2213, 2309, 2417, 2531, 2633, 2753, 2879, 3019, 3121, 3259, 3413, 3583, 3733, 3917, 4091, 4271, 4457, 4657, 4831, 5077, 5303, 5527, 5783, 6043, 6301, 6577, 6871, 7177, 7499, 7829, 8179, 8539, 8923, 9319, 9733, 10163, 10607, 11087, 11551, 12073, 12613, 13183, 13759, 14347, 15013, 15679, 16369, 17099, 17851, 18617, 19471, 20333, 21221, 22159, 23159, 24179, 25253, 26357, 27541, 28759, 30029, 31337, 32719, 34213, 35729, 37309, 38953, 40637, 42487, 44357, 46327, 48371, 50513, 52757, 55079, 57527, 60089, 62743, 65519, 68389, 71443, 74611, 77929, 81371, 84977, 88741, 92669, 96769, 101051, 105533, 110161, 115067, 120167, 125507, 131063, 136859, 142903, 149251, 155861, 162749, 169951, 177487, 185327, 193549, 202127, 211067, 220411, 230149, 240371, 250993, 262133, 273727, 285839, 298483, 311713, 325541, 339907, 354997, 370693, 387109, 404269, 422137, 440849, 460379, 480761, 502043, 524269, 547493, 571717, 597031, 623437, 651067, 679907, 709991, 741413, 774233, 808553, 844321, 881711, 920753, 961531, 1004089, 1048571, 1094983, 1143469, 1194059, 1246961, 1302179, 1359823, 1420031, 1482889, 1548541, 1617079, 1688677, 1763459, 1841531, 1923079, 2008219, 2097133, 2189987, 2286883, 2388181, 2493943, 2604307, 2719663, 2840059, 2965811, 3097121, 3234239, 3377401, 3526937, 3683111, 3846163, 4016473, 4194287, 4379971, 4573897, 4776347, 4987889, 5208701, 5439299, 5680151, 5931559, 6194213, 6468463, 6754877, 7053899, 7366217, 7692341, 8032939, 8388587, 8759957, 9147833, 9552821, 9975737, 10417427, 10878661, 11360303, 11863259, 12388507, 12936977, 13509689, 14107879, 14732483, 15384763, 16065893, 16777199, 17519989, 18295657, 19105673, 19951567, 20834899, 21757343, 22720597, 23726557, 24776993, 25873979, 27019537, 28215791, 29464999, 30769517, 32131817, 33554383, 35039981, 36591343, 38211377, 39903151, 41669743, 43514677, 45441223, 47453099, 49554047, 51747991, 54039077, 56431597, 58930021, 61539091, 64263611, 67108837, 70080013, 73182721, 76422793, 79806281, 83339653, 87029399, 90882523, 94906247, 99108041, 103495991, 108078133, 112863193, 117860077, 123078167, 128527309, 134217649, 140159989, 146365423, 152845529, 159612647, 166679243, 174058849, 181765093, 189812501, 198216169, 206992007, 216156329, 225726377, 235720159, 246156367, 257054659, 268435367, 280320097, 292730831, 305691181, 319225297, 333358643, 348117709, 363530147, 379625041, 396432481, 413984041, 432312677, 451452797, 471440303, 492312731, 514109273, 536870879, 560640139, 585461857, 611382463, 638450671, 666717323, 696235427, 727060331, 759250091, 792864983, 827968087, 864625379, 902905637, 942880663, 984625553, 1028218643, 1073741783, 1121280343, 1170923711, 1222764913, 1276901371, 1333434647, 1392470797, 1454120807, 1518500183, 1585729991, 1655936251, 1729250813, 1805811253, 1885761313, 1969251133, 2056437377, 2147483629 };

}

