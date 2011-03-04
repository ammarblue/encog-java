package org.encog.app.analyst.script;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.encog.app.analyst.script.prop.PropertyConstraints;
import org.encog.app.analyst.script.prop.PropertyEntry;
import org.encog.app.analyst.script.prop.ScriptProperties;
import org.encog.app.analyst.script.segregate.AnalystSegregateTarget;
import org.encog.app.analyst.script.task.AnalystTask;
import org.encog.app.quant.normalize.NormalizedField;

public class ScriptSave {

	private AnalystScript script;

	public ScriptSave(AnalystScript script) {
		this.script = script;
	}

	private void saveConfig(WriteScriptFile out) {
		saveSubSection(out,"SETUP","CONFIG");
		out.addSubSection("FILENAMES");
		
		List<String> list = this.script.getProperties().getFilenames();
		
		for (String key : list) {
			String value = this.script.getProperties().getFilename(key);
			out.writeProperty(key, value);
		}
	}
	
	
	private void saveSubSection(WriteScriptFile out, String section, String subSection)
	{
		if( !section.equals(out.getCurrentSection()) )
			out.addSection(section);
		out.addSubSection(subSection);
		List<PropertyEntry> list = PropertyConstraints.getInstance().getEntries(section,subSection);
		Collections.sort(list);
		for(PropertyEntry entry:list) {
			String key = section + ":" + subSection + "_" + entry.getName();
			String value = this.script.getProperties().getPropertyString(key);
			if( value!=null ) {
				out.writeProperty(entry.getName(), value);
			} else {
				out.writeProperty(entry.getName(), "");
			}
		}
	}
		
	private void saveSegregate(WriteScriptFile out)
	{
		saveSubSection(out,"SEGREGATE","CONFIG");
		out.addSubSection("FILES");
		out.addColumn("file");
		out.addColumn("percent");
		out.writeLine();
		
		for(AnalystSegregateTarget target: this.script.getSegregate().getSegregateTargets() ) {
			out.addColumn(target.getFile());
			out.addColumn(target.getPercent());
			out.writeLine();
		}
	}
	
	private void saveMachineLearning(WriteScriptFile out)
	{
		saveSubSection(out,"ML","CONFIG");
		saveSubSection(out,"ML","TRAIN");
		
	}

	private void saveData(WriteScriptFile out) {
		out.addSection("DATA");
		out.addSubSection("STATS");
		out.addColumn("name");
		out.addColumn("isclass");
		out.addColumn("iscomplete");
		out.addColumn("isint");
		out.addColumn("isreal");
		out.addColumn("amax");
		out.addColumn("amin");
		out.addColumn("mean");
		out.addColumn("sdev");
		out.writeLine();

		for (DataField field : this.script.getFields()) {
			out.addColumn(field.getName());
			out.addColumn(field.isClass());
			out.addColumn(field.isComplete());
			out.addColumn(field.isInteger());
			out.addColumn(field.isReal());
			out.addColumn(field.getMax());
			out.addColumn(field.getMin());
			out.addColumn(field.getMean());
			out.addColumn(field.getStandardDeviation());
			out.writeLine();
		}
		out.flush();

		out.addSubSection("CLASSES");
		out.addColumn("field");
		out.addColumn("code");
		out.addColumn("name");
		out.writeLine();
		
		for (DataField field : this.script.getFields()) {
			if (field.isClass()) {
				for(AnalystClassItem col: field.getClassMembers() ) {
					out.addColumn(field.getName());
					out.addColumn(col.getCode());
					out.addColumn(col.getName());
					out.writeLine();
				}
			}
		}

	}
	
	private void saveTasks(WriteScriptFile out) {
		out.addSection("TASKS");
		List<String> list = new ArrayList<String>();
		list.addAll(this.script.getTasks().keySet());
		Collections.sort(list);
		for(String key: list) {
			AnalystTask task = this.script.getTask(key);
			out.addSubSection(task.getName());
			for(String line: task.getLines()) {
				out.addLine(line);
			}
		}
	}

	private void saveNormalize(WriteScriptFile out) {
		saveSubSection(out,"NORMALIZE","CONFIG");

		out.addSubSection("RANGE");
		out.addColumn("name");
		out.addColumn("action");
		out.addColumn("high");
		out.addColumn("low");
		out.writeLine();
		for (NormalizedField field : this.script.getNormalize()
				.getNormalizedFields()) {
			out.addColumn(field.getName());
			switch (field.getAction()) {
			case Ignore:
				out.addColumn("ignore");
				break;
			case Normalize:
				out.addColumn("range");
				break;
			case PassThrough:
				out.addColumn("pass");
				break;
			case OneOf:
				out.addColumn("oneof");
				break;
			case Equilateral:
				out.addColumn("equilateral");
				break;
			case SingleField:
				out.addColumn("single");
				break;			
			}

			out.addColumn(field.getNormalizedHigh());
			out.addColumn(field.getNormalizedLow());
			out.writeLine();
		}
	}

	public void save(OutputStream stream) {
		WriteScriptFile out = new WriteScriptFile(stream);
		saveSubSection(out,"HEADER","DATASOURCE");
		saveConfig(out);
		saveData(out);
		saveNormalize(out);
		saveSubSection(out,"RANDOMIZE","CONFIG");
		saveSegregate(out);
		saveSubSection(out,"GENERATE","CONFIG");
		saveMachineLearning(out);
		saveTasks(out);
		out.flush();
	}
}