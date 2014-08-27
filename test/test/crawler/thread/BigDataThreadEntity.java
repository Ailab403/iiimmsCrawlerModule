package test.crawler.thread;

import java.util.concurrent.Callable;

public class BigDataThreadEntity implements Callable<Integer> {

	private Integer flag = new Integer(0);

	public BigDataThreadEntity(Integer flag) {
		super();
		this.flag = flag;
	}

	public Integer call() throws Exception {
		for (int i = 0; i < 100; i++) {
			System.out.println(this.flag + ":" + i);
		}

		return this.flag;
	}
}
