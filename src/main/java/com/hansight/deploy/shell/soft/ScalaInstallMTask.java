package com.hansight.deploy.shell.soft;

import java.util.ArrayList;
import java.util.List;

import com.hansight.deploy.shell.base.MultiThreadTask;
import com.hansight.deploy.shell.base.ShellTask;
import com.hansight.deploy.shell.env.EnvPathConfig;
import com.hansight.deploy.shell.install.CompressedFileInstall;
import com.hansight.deploy.vo.Status;

public class ScalaInstallMTask extends MultiThreadTask implements ShellTask {
	private String packName = "scala-2.10.4.tgz";
	private String unzippedName = "scala-2.10.4";
	private String lnName = "scala";

	@Override
	public Status execute() {
		// TODO user defined install path
		// upload and unpack
		Status status = CompressedFileInstall.install(host, env, "/opt",
				packName, unzippedName, lnName, false);
		if (!status.isSuccess()) {
			return status;
		}
		List<String> exports = new ArrayList<>();
		exports.add("#SCALA setting");
		exports.add("export SCALA_HOME=/opt/" + lnName);
		exports.add("export PATH=$SCALA_HOME/bin:$PATH");
		return new EnvPathConfig(env, host, exports).execute();
	}

	@Override
	public Status call() throws Exception {
		return execute();
	}

	@Override
	protected MultiThreadTask clone() {
		return new ScalaInstallMTask();
	}

}
