package fr.opensagres.language.textmate.grammar.parser;

import java.util.Collection;
import java.util.HashMap;

import fr.opensagres.language.textmate.types.IRawCaptures;
import fr.opensagres.language.textmate.types.IRawGrammar;
import fr.opensagres.language.textmate.types.IRawRepository;
import fr.opensagres.language.textmate.types.IRawRule;

public class Raw extends HashMap<String, Object> implements IRawRepository, IRawRule, IRawGrammar, IRawCaptures {

	@Override
	public IRawRule getProp(String name) {
		return (IRawRule) super.get(name);
	}

	@Override
	public IRawRule getBase() {
		return (IRawRule) super.get("$base");
	}

	@Override
	public void setBase(IRawRule base) {
		super.put("$base", base);
	}

	@Override
	public IRawRule getSelf() {
		return (IRawRule) super.get("$self");
	}

	@Override
	public void setSelf(IRawRule self) {
		super.put("$self", self);
	}

	@Override
	public Integer getId() {
		return (Integer) super.get("id");
	}

	@Override
	public void setId(Integer id) {
		super.put("id", id);
	}

	@Override
	public String getName() {
		return (String) super.get("name");
	}

	@Override
	public void setName(String name) {
		super.put("name", name);
	}

	@Override
	public String getContentName() {
		return (String) super.get("contentName");
	}

	@Override
	public void setContentName(String name) {
		super.put("contentName", name);
	}

	@Override
	public String getMatch() {
		return (String) super.get("match");
	}

	@Override
	public void setMatch(String match) {
		super.put("match", match);
	}

	@Override
	public IRawCaptures getCaptures() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCaptures(IRawCaptures captures) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getBegin() {
		return (String) super.get("begin");
	}

	@Override
	public void setBegin(String begin) {
		super.put("begin", begin);
	}

	@Override
	public String getInclude() {
		return (String) super.get("include");
	}

	@Override
	public void setInclude(String include) {
		super.put("include", include);
	}

	@Override
	public IRawCaptures getBeginCaptures() {
		return (IRawCaptures) super.get("beginCaptures");
	}

	@Override
	public void setBeginCaptures(IRawCaptures beginCaptures) {
		super.put("beginCaptures", beginCaptures);
	}

	@Override
	public String getEnd() {
		return (String) super.get("end");
	}

	@Override
	public void setEnd(String end) {
		super.put("end", end);
	}

	@Override
	public IRawCaptures getEndCaptures() {
		return (IRawCaptures) super.get("endCaptures");
	}

	@Override
	public void setEndCaptures(IRawCaptures endCaptures) {
		super.put("endCaptures", endCaptures);
	}

	@Override
	public Collection<IRawRule> getPatterns() {
		return (Collection<IRawRule>) super.get("patterns");
	}

	@Override
	public void setPatterns(Collection<IRawRule> patterns) {
		super.put("patterns", patterns);
	}

	@Override
	public IRawRepository getRepository() {
		return (IRawRepository) super.get("repository");
	}

	@Override
	public void setRepository(IRawRepository repository) {
		super.put("repository", repository);
	}

	@Override
	public Boolean getApplyEndPatternLast() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setApplyEndPatternLast(Boolean applyEndPatternLast) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getScopeName() {
		return (String) super.get("scopeName");
	}

	@Override
	public String[] getFileTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFirstLineMatch() {
		// TODO Auto-generated method stub
		return null;
	}

}