// --== CS400 File Header Information ==--
// Name: Sanghun Chung
// Email: schung72@wisc.edu
// Team: Blue
// Role: Data Wrangler
// TA: TA Mohan
// Lecturer: Florian



import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.zip.DataFormatException;

public interface BusRouteDataReaderInterface {

  /**
   * Parses the input files/strings for the bus routes and returns a "graphable" format of the data
   * 
   * @param busReader 
   * @return "graphable" data that represents the bus routes
   * @throws IOException         if any of the input readers cannot be read
   * @throws DataFormatException if the data format of any of the input readers is incorrect
   */
  BusRouteDataInterface readDataSet(Reader busReader) throws IOException, DataFormatException;
}