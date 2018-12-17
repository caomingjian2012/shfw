package gui.gui;

 

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.experimental.chart.swt.ChartComposite;

public class TimeSeriesDemo2 {
 

	private static JFreeChart createChart(XYDataset var0) {
		 
		BufferedImage image = new BufferedImage(200 , 100,
                BufferedImage.TYPE_INT_RGB);
       final Graphics2D g2 = image.createGraphics();
       final JFreeChart chart = ChartFactory.createTimeSeriesChart("Legal & General Unit Trust Prices", "Date",
				"Price Per Unit", createDataset(), true, true, false);
		XYPlot var2 = (XYPlot) chart.getPlot();
		var2.setDomainPannable(true);
		var2.setRangePannable(false);
		var2.setDomainCrosshairVisible(true);
		var2.setRangeCrosshairVisible(true);
		new Thread(new Runnable() {
			
			public void run() {
				 chart.draw(g2, new Rectangle2D.Double(0, 0, 200, 100), null,
			                null);
				
			}
		}).start();
       
        g2.dispose();
		
		return chart;
	}
	
	private static JFreeChart createChart2(XYDataset var0) {
		 
		Image image = new Image(Display.getDefault(), "aaa");
       final Graphics2D g2 = image.createGraphics();
       final JFreeChart chart = ChartFactory.createTimeSeriesChart("Legal & General Unit Trust Prices", "Date",
				"Price Per Unit", createDataset(), true, true, false);
		XYPlot var2 = (XYPlot) chart.getPlot();
		var2.setDomainPannable(true);
		var2.setRangePannable(false);
		var2.setDomainCrosshairVisible(true);
		var2.setRangeCrosshairVisible(true);
		new Thread(new Runnable() {
			
			public void run() {
				 chart.draw(g2, new Rectangle2D.Double(0, 0, 200, 100), null,
			                null);
				
			}
		}).start();
       
        g2.dispose();
		
		return chart;
	}

	private static XYDataset createDataset() {
		TimeSeries var0 = new TimeSeries("L&G European Index Trust");
		var0.add(new Month(2, 2001), 181.8D);
		var0.add(new Month(3, 2001), 167.3D);
		var0.add(new Month(4, 2001), 153.8D);
		var0.add(new Month(5, 2001), 167.6D);
		var0.add(new Month(6, 2001), 158.8D);
		var0.add(new Month(7, 2001), 148.3D);
		var0.add(new Month(8, 2001), 153.9D);
		var0.add(new Month(9, 2001), 142.7D);
		var0.add(new Month(10, 2001), 123.2D);
		var0.add(new Month(11, 2001), 131.8D);
		var0.add(new Month(12, 2001), 139.6D);
		var0.add(new Month(1, 2002), 142.9D);
		var0.add(new Month(2, 2002), 138.7D);
		var0.add(new Month(3, 2002), 137.3D);
		var0.add(new Month(4, 2002), 143.9D);
		var0.add(new Month(5, 2002), 139.8D);
		var0.add(new Month(6, 2002), 137.0D);
		var0.add(new Month(7, 2002), 132.8D);
		var0.add(new Month(8, 2002), 110.3D);
		var0.add(new Month(9, 2002), 110.5D);
		var0.add(new Month(10, 2002), 94.11D);
		var0.add(new Month(11, 2002), 102.5D);
		var0.add(new Month(12, 2002), 112.3D);
		var0.add(new Month(1, 2003), 104.0D);
		var0.add(new Month(2, 2003), 98.53D);
		var0.add(new Month(3, 2003), 97.15D);
		var0.add(new Month(4, 2003), 94.9D);
		var0.add(new Month(5, 2003), 107.8D);
		var0.add(new Month(6, 2003), 113.7D);
		var0.add(new Month(7, 2003), 112.5D);
		var0.add(new Month(8, 2003), 118.6D);
		var0.add(new Month(9, 2003), 123.8D);
		var0.add(new Month(10, 2003), 117.2D);
		var0.add(new Month(11, 2003), 123.0D);
		var0.add(new Month(12, 2003), 127.0D);
		var0.add(new Month(1, 2004), 132.7D);
		var0.add(new Month(2, 2004), 132.4D);
		var0.add(new Month(3, 2004), 131.7D);
		var0.add(new Month(4, 2004), 128.0D);
		var0.add(new Month(5, 2004), 131.8D);
		var0.add(new Month(6, 2004), 127.4D);
		var0.add(new Month(7, 2004), 133.5D);
		var0.add(new Month(8, 2004), 126.0D);
		var0.add(new Month(9, 2004), 129.5D);
		var0.add(new Month(10, 2004), 135.3D);
		var0.add(new Month(11, 2004), 138.0D);
		var0.add(new Month(12, 2004), 141.3D);
		var0.add(new Month(1, 2005), 148.8D);
		var0.add(new Month(2, 2005), 147.1D);
		var0.add(new Month(3, 2005), 150.7D);
		var0.add(new Month(4, 2005), 150.0D);
		var0.add(new Month(5, 2005), 145.7D);
		var0.add(new Month(6, 2005), 152.0D);
		var0.add(new Month(7, 2005), 157.2D);
		var0.add(new Month(8, 2005), 167.0D);
		var0.add(new Month(9, 2005), 165.0D);
		var0.add(new Month(10, 2005), 171.6D);
		var0.add(new Month(11, 2005), 166.2D);
		var0.add(new Month(12, 2005), 174.3D);
		var0.add(new Month(1, 2006), 183.8D);
		var0.add(new Month(2, 2006), 187.0D);
		var0.add(new Month(3, 2006), 191.3D);
		var0.add(new Month(4, 2006), 202.5D);
		var0.add(new Month(5, 2006), 200.6D);
		var0.add(new Month(6, 2006), 187.3D);
		var0.add(new Month(7, 2006), 192.2D);
		var0.add(new Month(8, 2006), 190.8D);
		var0.add(new Month(9, 2006), 194.7D);
		var0.add(new Month(10, 2006), 201.3D);
		var0.add(new Month(11, 2006), 205.1D);
		var0.add(new Month(12, 2006), 206.7D);
		var0.add(new Month(1, 2007), 216.8D);
		var0.add(new Month(2, 2007), 218.0D);
		var0.add(new Month(3, 2007), 215.4D);
		var0.add(new Month(4, 2007), 223.0D);
		var0.add(new Month(5, 2007), 235.1D);
		var0.add(new Month(6, 2007), 242.0D);
		var0.add(new Month(7, 2007), 237.8D);
		TimeSeries var1 = new TimeSeries("L&G UK Index Trust");
		var1.add(new Month(2, 2001), 129.6D);
		var1.add(new Month(3, 2001), 123.2D);
		var1.add(new Month(4, 2001), 117.2D);
		var1.add(new Month(5, 2001), 124.1D);
		var1.add(new Month(6, 2001), 122.6D);
		var1.add(new Month(7, 2001), 119.2D);
		var1.add(new Month(8, 2001), 116.5D);
		var1.add(new Month(9, 2001), 112.7D);
		var1.add(new Month(10, 2001), 101.5D);
		var1.add(new Month(11, 2001), 106.1D);
		var1.add(new Month(12, 2001), 110.3D);
		var1.add(new Month(1, 2002), 111.7D);
		var1.add(new Month(2, 2002), 111.0D);
		var1.add(new Month(3, 2002), 109.6D);
		var1.add(new Month(4, 2002), 113.2D);
		var1.add(new Month(5, 2002), 111.6D);
		var1.add(new Month(6, 2002), 108.8D);
		var1.add(new Month(7, 2002), 101.6D);
		var1.add(new Month(8, 2002), 90.95D);
		var1.add(new Month(9, 2002), 91.02D);
		var1.add(new Month(10, 2002), 82.37D);
		var1.add(new Month(11, 2002), 86.32D);
		var1.add(new Month(12, 2002), 91.0D);
		var1.add(new Month(1, 2003), 86.0D);
		var1.add(new Month(2, 2003), 80.04D);
		var1.add(new Month(3, 2003), 80.4D);
		var1.add(new Month(4, 2003), 80.28D);
		var1.add(new Month(5, 2003), 86.42D);
		var1.add(new Month(6, 2003), 91.4D);
		var1.add(new Month(7, 2003), 90.52D);
		var1.add(new Month(8, 2003), 93.11D);
		var1.add(new Month(9, 2003), 96.8D);
		var1.add(new Month(10, 2003), 94.78D);
		var1.add(new Month(11, 2003), 99.56D);
		var1.add(new Month(12, 2003), 100.8D);
		var1.add(new Month(1, 2004), 103.4D);
		var1.add(new Month(2, 2004), 102.1D);
		var1.add(new Month(3, 2004), 105.3D);
		var1.add(new Month(4, 2004), 103.7D);
		var1.add(new Month(5, 2004), 105.2D);
		var1.add(new Month(6, 2004), 103.7D);
		var1.add(new Month(7, 2004), 105.7D);
		var1.add(new Month(8, 2004), 103.6D);
		var1.add(new Month(9, 2004), 106.1D);
		var1.add(new Month(10, 2004), 109.3D);
		var1.add(new Month(11, 2004), 110.3D);
		var1.add(new Month(12, 2004), 112.6D);
		var1.add(new Month(1, 2005), 116.0D);
		var1.add(new Month(2, 2005), 117.3D);
		var1.add(new Month(3, 2005), 120.1D);
		var1.add(new Month(4, 2005), 119.3D);
		var1.add(new Month(5, 2005), 116.2D);
		var1.add(new Month(6, 2005), 120.8D);
		var1.add(new Month(7, 2005), 125.2D);
		var1.add(new Month(8, 2005), 127.7D);
		var1.add(new Month(9, 2005), 130.8D);
		var1.add(new Month(10, 2005), 131.0D);
		var1.add(new Month(11, 2005), 135.3D);
		var1.add(new Month(12, 2005), 141.2D);
		var1.add(new Month(1, 2006), 144.7D);
		var1.add(new Month(2, 2006), 146.4D);
		var1.add(new Month(3, 2006), 151.9D);
		var1.add(new Month(4, 2006), 153.5D);
		var1.add(new Month(5, 2006), 144.5D);
		var1.add(new Month(6, 2006), 150.1D);
		var1.add(new Month(7, 2006), 148.7D);
		var1.add(new Month(8, 2006), 150.1D);
		var1.add(new Month(9, 2006), 151.6D);
		var1.add(new Month(10, 2006), 153.4D);
		var1.add(new Month(11, 2006), 158.3D);
		var1.add(new Month(12, 2006), 157.6D);
		var1.add(new Month(1, 2007), 163.9D);
		var1.add(new Month(2, 2007), 163.8D);
		var1.add(new Month(3, 2007), 162.0D);
		var1.add(new Month(4, 2007), 167.1D);
		var1.add(new Month(5, 2007), 170.0D);
		var1.add(new Month(6, 2007), 175.7D);
		var1.add(new Month(7, 2007), 171.9D);
		TimeSeriesCollection var2 = new TimeSeriesCollection();
		var2.addSeries(var0);
		var2.addSeries(var1);
		return var2;
	}

	public static JPanel createDemoPanel() {
		JFreeChart var0 = createChart(createDataset());
		return new ChartPanel(var0);
	}

	public static void main(String[] var0) {
		         JFreeChart chart = createChart(createDataset());
		        Display display = new Display();
		         Shell shell = new Shell(display);
		         shell.setSize(600, 300);
		         shell.setLayout(new FillLayout());
		         shell.setText("Test for jfreechart running with SWT");
		         final ChartComposite frame = new ChartComposite(shell, SWT.NONE, chart,
		                 true);
		        frame.pack();
		         shell.open();
		         while (!shell.isDisposed()) {
		           if (!display.readAndDispatch())
		                 display.sleep();
	}
	}
}
