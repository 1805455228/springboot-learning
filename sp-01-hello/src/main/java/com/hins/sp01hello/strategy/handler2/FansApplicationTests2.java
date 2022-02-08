package com.hins.sp01hello.strategy.handler2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/strategy2")
public class FansApplicationTests2 {

	@GetMapping("/noDesign2")
	public String noDesign2() {
		String name = "张三";

		if (name.equals("张三")) {

			// 业务逻辑B
			return "张三完成任务BBB";

		} else if (name.equals("李四")) {

			// 业务逻辑B
			return "李四完成任务BBB";

		} else if (name.equals("王五")) {

			// 业务逻辑B
			return "王五完成任务BBB";

		} else if (name.equals("赵六")) {

			// 业务逻辑A
			System.out.println("赵六完成任务AAA");

		} else if (name.equals("田七")) {

			// 业务逻辑A
			System.out.println("田七完成任务AAA");

		} else if (name.equals("亢八")) {

			// 业务逻辑A
			System.out.println("亢八完成任务AAA");

		}
		return "end";
	}

	// 工厂 + 策略 + 模板设计模式
	@GetMapping("/design2")
	public void design2() {
		String name = "张三";

		AbstractHandler strategy = Factory2.getInvokeStrategy(name);

//		strategy.AAA(name);

		String str = strategy.BBB(name);

		System.out.println(str);
	}
}