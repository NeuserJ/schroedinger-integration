package org.schrodinger.gui;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.schrodinger.Einstellungen;
import org.schrodinger.SchroedingerIntegration;
import org.schrodinger.potential.Coulomb;
import org.schrodinger.potential.CoulombL;
import org.schrodinger.potential.Kasten;
import org.schrodinger.potential.Parabel;
import org.schrodinger.potential.PeriodicPotential;
import org.schrodinger.potential.Potential;
import org.schrodinger.potential.UserFunction;

import javax.swing.JCheckBox;

public class Potentialeinstellungen extends JFrame {

	private JPanel contentPane;
	private enum Potentialarten{Coulomb, Kasten, Parabel, benutzerdefiniert}
	
	private Potentialarten potentialAuswahl;
	
	private JTextField coulomb1, kastenBoden, kastenBreite, kastenHoehe, benutzerdefiniert,
			parabelTiefe, parabelBreite;
	private Potential potential;
	private JCheckBox chckbxPeriodischesPotential;
	
	private double e = Einstellungen.e;
	private JTextField E_min_Eingabe;
	private JTextField E_max_Eingabe;
	private JTextField TextPeriodAnz;
	private JTextField TextPeriodDist;

	private JCheckBox coulomb2;


	/**
	 * Create the frame.
	 */
	public Potentialeinstellungen() {
		this.potential = SchroedingerIntegration.potential;
		
		setTitle("Einstellungen zum Potential");
		setBounds(50, 50, 650, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel Erklaerung = new JLabel("Hier koennen Einstellungen fuer das Potential gemacht werden:");
		Erklaerung.setBounds(10, 11, 414, 28);
		contentPane.add(Erklaerung);
		
		JLabel lblAuswahlDerPotentialart = new JLabel("Auswahl der Potentialart");
		lblAuswahlDerPotentialart.setBounds(10, 38, 160, 22);
		contentPane.add(lblAuswahlDerPotentialart);
		
		ButtonGroup bg = new ButtonGroup();
		
		final JPanel settings = new JPanel();
		settings.setBounds(202, 70, 422, 281);
		settings.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		contentPane.add(settings);
		
		JRadioButton potOpt1 = new JRadioButton("Coulomb");
		potOpt1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(arg0.getStateChange() == ItemEvent.SELECTED){
					potentialAuswahl = Potentialarten.Coulomb;
					drawDetails(settings);
				}
			}
		});
		potOpt1.setBounds(10, 63, 106, 17);
		contentPane.add(potOpt1);
		bg.add(potOpt1);
		
		JRadioButton potOpt2 = new JRadioButton("Kasten");
		potOpt2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(arg0.getStateChange() == ItemEvent.SELECTED){
					potentialAuswahl = Potentialarten.Kasten;
					drawDetails(settings);
				}
			}
		});
		potOpt2.setBounds(10, 83, 109, 17);
		contentPane.add(potOpt2);
		bg.add(potOpt2);
		
		JRadioButton potOpt3 = new JRadioButton("Parabel");
		potOpt3.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(arg0.getStateChange() == ItemEvent.SELECTED){
					potentialAuswahl = Potentialarten.Parabel;
					drawDetails(settings);
				}
			}
		});
		potOpt3.setBounds(10, 103, 101, 17);
		contentPane.add(potOpt3);
		bg.add(potOpt3);
		
		
		JRadioButton potOpt4 = new JRadioButton("benutzerdefiniert");
		potOpt4.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(arg0.getStateChange() == ItemEvent.SELECTED){
					potentialAuswahl = Potentialarten.benutzerdefiniert;
					drawDetails(settings);
				}
			}
		});
		potOpt4.setBounds(10, 123, 160, 17);
		contentPane.add(potOpt4);
		bg.add(potOpt4);
		
		JLabel lblDetails = new JLabel("Details:");
		lblDetails.setBounds(201, 38, 117, 22);
		contentPane.add(lblDetails);
		
		if(potential.getClass().getName().contains("Coulomb")){
			potentialAuswahl = Potentialarten.Coulomb;
			potOpt1.setSelected(true);
		}else if(potential.getClass().getName().contains("Parabel")){
			potentialAuswahl = Potentialarten.Parabel;
			potOpt3.setSelected(true);
		}else if(potential.getClass().getName().contains("Kasten")){
			potentialAuswahl = Potentialarten.Kasten;
			potOpt2.setSelected(true);
		}else if(potential.getClass().getName().contains("UserFunction")){
			potentialAuswahl = Potentialarten.benutzerdefiniert;
			potOpt4.setSelected(true);
		}else {
			potentialAuswahl = Potentialarten.Coulomb;
			potOpt1.setSelected(true);
		}
				
		drawDetails(settings);
		
		JButton btnNewButton = new JButton("Fertig");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				finish();			
			}
		});
		btnNewButton.setBounds(10, 328, 77, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Abbrechen");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				dispose();
			}
		});
		btnNewButton_1.setBounds(97, 328, 101, 23);
		contentPane.add(btnNewButton_1);
		
		JLabel lblEnergienFuerDie = new JLabel("Energien fuer die Suche in eV:");
		lblEnergienFuerDie.setBounds(10, 244, 182, 14);
		contentPane.add(lblEnergienFuerDie);
		
		JLabel lblMinimum = new JLabel("Minimum");
		lblMinimum.setBounds(10, 269, 64, 20);
		contentPane.add(lblMinimum);
		
		JLabel lblMaximum = new JLabel("Maximum");
		lblMaximum.setBounds(10, 300, 64, 17);
		contentPane.add(lblMaximum);
		
		E_min_Eingabe = new JTextField();
		E_min_Eingabe.setText(Double.toString(Einstellungen.E_min/e));
		E_min_Eingabe.setBounds(97, 269, 86, 20);
		contentPane.add(E_min_Eingabe);
		E_min_Eingabe.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				try{
					double d = Double.parseDouble(E_min_Eingabe.getText());
					if(d>Double.parseDouble(E_max_Eingabe.getText())){
						E_min_Eingabe.setText(new DecimalFormat("#######.########").format(Einstellungen.E_min/e));
					}
				}catch(Exception exception){
					E_min_Eingabe.setText(Double.toString(Einstellungen.E_max/e));
				}
			}
		});
		E_min_Eingabe.setColumns(10);
		
		E_max_Eingabe = new JTextField();
		E_max_Eingabe.setText(Double.toString(Einstellungen.E_max/e));
		E_max_Eingabe.setColumns(10);
		E_max_Eingabe.setBounds(97, 297, 86, 20);
		E_max_Eingabe.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				try{
					double d = Double.parseDouble(E_max_Eingabe.getText());
					if(d<Double.parseDouble(E_min_Eingabe.getText())){
						E_max_Eingabe.setText(new DecimalFormat("#######.########").format(Einstellungen.E_max/e));
					}
				}catch(Exception exception){
					E_max_Eingabe.setText(Double.toString(Einstellungen.E_max/e));
				}
			}
		});
		contentPane.add(E_max_Eingabe);
		
		chckbxPeriodischesPotential = new JCheckBox("periodisches Potential");
		chckbxPeriodischesPotential.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(arg0.getStateChange() == ItemEvent.DESELECTED){
					TextPeriodAnz.setEnabled(false);
					TextPeriodDist.setEnabled(false);
				}else if(arg0.getStateChange()==ItemEvent.SELECTED){
					TextPeriodAnz.setEnabled(true);
					TextPeriodDist.setEnabled(true);
				}
			}
		});
		chckbxPeriodischesPotential.setBounds(10, 155, 177, 23);
		contentPane.add(chckbxPeriodischesPotential);
		
		
		JLabel lblAnzahl = new JLabel("Anzahl:");
		lblAnzahl.setBounds(10, 185, 77, 14);
		contentPane.add(lblAnzahl);
		
		JLabel lblAbstandInNm = new JLabel("Abstand in nm:");
		lblAbstandInNm.setBounds(10, 210, 86, 14);
		contentPane.add(lblAbstandInNm);
		
		TextPeriodAnz = new JTextField();
		TextPeriodAnz.setEnabled(false);
		TextPeriodAnz.setText("5");
		TextPeriodAnz.setBounds(97, 185, 86, 20);
		contentPane.add(TextPeriodAnz);
		TextPeriodAnz.setColumns(10);
		TextPeriodAnz.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				try{
					Double.parseDouble(TextPeriodAnz.getText());
				}catch(Exception exception){
					TextPeriodAnz.setText("5");
				}
			}
		});
		
		TextPeriodDist = new JTextField();
		TextPeriodDist.setEnabled(false);
		TextPeriodDist.setText("0.5");
		TextPeriodDist.setColumns(10);
		TextPeriodDist.setBounds(97, 213, 86, 20);
		contentPane.add(TextPeriodDist);
		TextPeriodDist.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				try{
					Double.parseDouble(TextPeriodDist.getText());
				}catch(Exception exception){
					TextPeriodDist.setText("20");
				}
			}
		});
		
	}


	protected void finish() {
		this.dispose();
		switch(potentialAuswahl){
		case Coulomb:
			potential = new CoulombL(Double.parseDouble(coulomb1.getText())*e);
			Einstellungen.divideR = coulomb2.isSelected();
			break;
		case Kasten:
			potential = new Kasten(Double.parseDouble(kastenBoden.getText())*e,Double.parseDouble(kastenHoehe.getText())*e,Double.parseDouble(kastenBreite.getText())*1E-9);
			break;
		case Parabel:
			potential = new Parabel(Double.parseDouble(parabelTiefe.getText())*e, Double.parseDouble(parabelBreite.getText()));
			break;
		case benutzerdefiniert:
			// TODO Potential erstellen

			Funktion f = new Funktion(benutzerdefiniert.getText(), true);
			potential = new UserFunction(f);
			break;
		default:
			potential = null;
			break;	
		}
		Einstellungen.E_max = Double.parseDouble(E_max_Eingabe.getText())*e;
		Einstellungen.E_min = Double.parseDouble(E_min_Eingabe.getText())*e;
		if(chckbxPeriodischesPotential.isSelected()){
			potential = new PeriodicPotential(Integer.parseInt(TextPeriodAnz.getText()), 1E-9*Double.parseDouble(TextPeriodDist.getText()), potential);
		}
		SchroedingerIntegration.potential = potential;
	}


	private void drawDetails(JPanel settings) {
		settings.removeAll();
		LayoutManager l = settings.getLayout();
		settings.removeAll();
		settings.setLayout(l);

		switch(potentialAuswahl){
		case Coulomb:
			JLabel label1 = new JLabel("Ladung des Kerns in e:");
			label1.setBounds(5,5,200,20);
			settings.add(label1);
			
			coulomb1 = new JTextField("1");
			coulomb1.setBounds(205,5,50,20);
			settings.add(coulomb1);
			coulomb1.setColumns(4);
			coulomb1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try{
						double charge = Double.parseDouble(coulomb1.getText());
						if(charge <0 && (Einstellungen.E_max <0 || Einstellungen.E_min < 0)){
							JOptionPane.showMessageDialog(null, "Warnung: Suche im Bereich mit ungebunden Zustaenden","Warnung", JOptionPane.WARNING_MESSAGE);
						}else if (charge > 0 && (Einstellungen.E_max >0 || Einstellungen.E_min > 0)){
							JOptionPane.showMessageDialog(null, "Warnung: Suche im Bereich mit ungebunden Zustaenden","Warnung", JOptionPane.WARNING_MESSAGE);
						}else if (charge == 0){
							JOptionPane.showMessageDialog(null, "Warnung: Kern traegt keine Ladung","Warnung", JOptionPane.WARNING_MESSAGE);
						}
					}catch(Exception exception){
						JOptionPane.showMessageDialog(null, "Error: Geben Sie eine gueltige Zahl ein","Error", JOptionPane.ERROR_MESSAGE);
						coulomb1.setText("1");
					}
				}
			});
			coulomb2 = new JCheckBox("durch Abstand dividieren");
			coulomb2.setBounds(205,30,50,20);
			coulomb2.setSelected(false);
			settings.add(coulomb2);
			break;
		case Kasten:
			JLabel label2 = new JLabel("unteres Niveau des Kasten in eV");
			label2.setBounds(5,5,200,10);
			settings.add(label2);
			
			kastenBoden = new JTextField("-20");
			kastenBoden.setBounds(205,5,50,20);
			settings.add(kastenBoden);
			kastenBoden.setColumns(4);
			kastenBoden.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try{
						double value = Double.parseDouble(kastenBoden.getText());
						if(value>Double.parseDouble(E_max_Eingabe.getText())){
							JOptionPane.showMessageDialog(null, "Warning: Kastenboden muss kleiner als obere Suchgrenze sein","Warning", JOptionPane.WARNING_MESSAGE);
						}
					}catch(Exception exception){
						JOptionPane.showMessageDialog(null, "Error: Geben Sie eine gueltige Zahl ein","Error", JOptionPane.ERROR_MESSAGE);
						kastenBoden.setText("-20");
					}
				}
			});
			
			JLabel label3 = new JLabel("Hoehe des Kasten in eV");
			label3.setBounds(5,30,200,20);
			settings.add(label3);
			
			kastenHoehe = new JTextField("30");
			kastenHoehe.setBounds(205,30,50,20);
			settings.add(kastenHoehe);
			kastenHoehe.setColumns(4);
			kastenHoehe.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try{
						double value = Double.parseDouble(kastenHoehe.getText());
						if(value<=0){
							JOptionPane.showMessageDialog(null, "Error: Hoehe muss positiv sein","Error", JOptionPane.WARNING_MESSAGE);
							kastenHoehe.setText("30");
						}
					}catch(Exception exception){
						JOptionPane.showMessageDialog(null, "Error: Geben Sie eine gueltige Zahl ein","Error", JOptionPane.ERROR_MESSAGE);
						kastenHoehe.setText("30");
					}
				}
			});
			
			JLabel label4 = new JLabel("Breite des Kasten in nm");
			label4.setBounds(5,55,200,20);
			settings.add(label4);
			
			kastenBreite = new JTextField("1");
			kastenBreite.setBounds(205,55,50,20);
			settings.add(kastenBreite);
			kastenBreite.setColumns(4);
			kastenBreite.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try{
						double value = Double.parseDouble(kastenBreite.getText());
						if(value<=0){
							JOptionPane.showMessageDialog(null, "Error: Breite muss positiv sein","Error", JOptionPane.WARNING_MESSAGE);
							kastenBreite.setText("1");
						}
					}catch(Exception exception){
						JOptionPane.showMessageDialog(null, "Error: Geben Sie eine gueltige Zahl ein","Error", JOptionPane.ERROR_MESSAGE);
						kastenBreite.setText("1");
					}
				}
			});
			break;
		case Parabel:
			
			E_min_Eingabe.setText("0");
			E_max_Eingabe.setText("20");
			
			JLabel lblparab1 = new JLabel("Tiefe des Scheitels: ");
			lblparab1.setBounds(5, 5, 200, 10);
			settings.add(lblparab1);
			
			parabelTiefe = new JTextField("0");
			parabelTiefe.setBounds(205,5,50,20);
			settings.add(parabelTiefe);
			parabelTiefe.setColumns(5);
			parabelTiefe.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try{
						double value = Double.parseDouble(kastenHoehe.getText());
						if(value<=0){
							JOptionPane.showMessageDialog(null, "Error: Tiefe muss positiv sein","Error", JOptionPane.WARNING_MESSAGE);
							parabelTiefe.setText("0");
						}
					}catch(Exception exception){
						JOptionPane.showMessageDialog(null, "Error: Geben Sie eine gueltige Zahl ein","Error", JOptionPane.ERROR_MESSAGE);
						parabelTiefe.setText("0");
					}
				}
			});
			
			JLabel lblparab2 = new JLabel("Breite der Parabel:");
			lblparab2.setBounds(5,30,200,20);
			settings.add(lblparab2);
			
			parabelBreite = new JTextField("1");
			parabelBreite.setBounds(205,30,50,20);
			settings.add(parabelBreite);
			parabelBreite.setColumns(4);
			parabelBreite.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try{
						double value = Double.parseDouble(parabelBreite.getText());
						if(value<=0){
							JOptionPane.showMessageDialog(null, "Error: Parabelbreite muss positiv sein","Error", JOptionPane.ERROR_MESSAGE);
							parabelBreite.setText("1");
						}
					}catch(Exception exception){
						JOptionPane.showMessageDialog(null, "Error: Geben Sie eine gueltige Zahl ein","Error", JOptionPane.ERROR_MESSAGE);
						parabelBreite.setText("1");
					}
				}
			});
			
			break;
		case benutzerdefiniert:
			JLabel lblben = new JLabel("<html>Hier kann ein benutzerdefiniertes Potential eingegeben werden."
					+ "<br>Die Variable x symbolisiert den Abstand vom Zentrum x=0<br>"
					+ "Als Konstanten koennen folgende Werte verwendet werden:"
					+ "<ul><li>q: Elementarladung</li>"
					+ "<li>u: Masse des Elektrons</li>"
					+ "<li>h: Plancksches Wirkungsquantum</li></ul><br>"
					+ "Mehr Informationen unter Hilfe im Hauptmenue</html>");
			lblben.setBounds(5,5,350,100);
			settings.add(lblben);
			
			JLabel Vx = new JLabel("V(x)=");
			Vx.setBounds(5, 105, 30, 20);
			settings.add(Vx);
			
			benutzerdefiniert = new JTextField("-q*q/(4*pi*e0*x)");
			benutzerdefiniert.setBounds(35,105,150,20);
			settings.add(benutzerdefiniert);
			benutzerdefiniert.setColumns(20);
			benutzerdefiniert.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Funktion f = new Funktion(benutzerdefiniert.getText(), true);
					if(!f.p.valid){
						JOptionPane.showMessageDialog(null, "Error: Syntaxfehler","Error", JOptionPane.WARNING_MESSAGE);
						benutzerdefiniert.setText("-q*q/(4*pi*e0*x)");
					}
				}
			});
			break;
		default:
			break;
		}
		repaint();
	}
}
