package de.jardas.commons.i18n;

import java.util.LinkedList;
import java.util.List;

import org.joda.time.ReadableInstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.support.DefaultMessageSourceResolvable;

public class DateHelper {
	private static final Logger LOG = LoggerFactory.getLogger(DateHelper.class);
	private static final long SECOND = 1000;
	private static final long MINUTE = 60 * SECOND;
	private static final long HOUR = 60 * MINUTE;
	private static final long DAY = 24 * HOUR;
	private static final long MONTH = 30 * DAY;
	private static final long YEAR = 365 * DAY;
	private static final int LESS_THAN_5_SECONDS = 5;
	private static final int LESS_THAN_10_SECONDS = 10;
	private static final int LESS_THAN_20_SECONDS = 20;

	private static enum Type {
		SECONDS_ONLY, MINUTES_ONLY, BOTH;
	}

	private final String messagePrefix = "time.distance.";
	private final List<Renderer> renderers = new LinkedList<Renderer>();

	public DateHelper() {
		renderers.add(new LessThanXSeconds(LESS_THAN_5_SECONDS));
		renderers.add(new LessThanXSeconds(LESS_THAN_10_SECONDS));
		renderers.add(new LessThanXSeconds(LESS_THAN_20_SECONDS));
		renderers.add(new HalfAMinute());
		renderers.add(new LessThanAMinute());
		renderers.add(new XMinutes());
		renderers.add(new AboutOneHour());
		renderers.add(new AboutXHours());
		renderers.add(new AboutOneDay());
		renderers.add(new AboutXDays());
		renderers.add(new AboutOneMonth());
		renderers.add(new AboutXMonths());
		renderers.add(new AboutOneYear());
		renderers.add(new OverXYears());
	}

	public MessageSourceResolvable distanceInWords(final ReadableInstant time, final ReadableInstant reference,
			final boolean includeSeconds) {
		return distanceInWords(time.getMillis(),
				reference != null ? reference.getMillis() : System.currentTimeMillis(), includeSeconds);
	}

	public MessageSourceResolvable distanceInWords(final long time, final long reference, final boolean includeSeconds) {
		final long distanceMilliseconds = Math.abs(reference - time);

		for (final Renderer renderer : renderers) {
			if (renderer.isApplicable(distanceMilliseconds, includeSeconds)) {
				LOG.debug("Rendering {} ms with {}", distanceMilliseconds, renderer);

				return renderer.render(distanceMilliseconds);
			}
		}

		return null;
	}

	private abstract class Renderer {
		private final long maxMilliseconds;
		private final Type type;

		protected Renderer(final long maxMilliseconds, final Type type) {
			this.maxMilliseconds = maxMilliseconds;
			this.type = type;
		}

		public boolean isApplicable(final long milliseconds, final boolean withSeconds) {
			if (withSeconds && type != Type.SECONDS_ONLY && type != Type.BOTH) {
				return false;
			}

			if (!withSeconds && type != Type.MINUTES_ONLY && type != Type.BOTH) {
				return false;
			}

			return maxMilliseconds < 0 || milliseconds < maxMilliseconds;
		}

		public abstract MessageSourceResolvable render(long milliseconds);
	}

	private class LessThanXSeconds extends Renderer {
		private final long seconds;

		public LessThanXSeconds(final int seconds) {
			super(seconds * SECOND, Type.SECONDS_ONLY);
			this.seconds = seconds;
		}

		@Override
		public MessageSourceResolvable render(final long milliseconds) {
			return new Message("less.than.x.seconds", seconds);
		}
	}

	private class HalfAMinute extends Renderer {
		private static final long MAX = 40 * SECOND;

		public HalfAMinute() {
			super(MAX, Type.SECONDS_ONLY);
		}

		@Override
		public MessageSourceResolvable render(final long milliseconds) {
			return new Message("half.a.minute");
		}
	}

	private class LessThanAMinute extends Renderer {
		public LessThanAMinute() {
			super(MINUTE, Type.BOTH);
		}

		@Override
		public MessageSourceResolvable render(final long milliseconds) {
			return new Message("less.than.x.minutes", 1L);
		}
	}

	private class XMinutes extends Renderer {
		private static final long MAX = 45 * MINUTE;

		public XMinutes() {
			super(MAX, Type.BOTH);
		}

		@Override
		public MessageSourceResolvable render(final long milliseconds) {
			return new Message("x.minutes", milliseconds / MINUTE);
		}
	}

	private class AboutOneHour extends Renderer {
		private static final long MAX = 89 * MINUTE;

		public AboutOneHour() {
			super(MAX, Type.BOTH);
		}

		@Override
		public MessageSourceResolvable render(final long milliseconds) {
			return new Message("about.x.hours", 1L);
		}
	}

	private class AboutXHours extends Renderer {
		public AboutXHours() {
			super(DAY, Type.BOTH);
		}

		@Override
		public MessageSourceResolvable render(final long milliseconds) {
			return new Message("about.x.hours", Math.round((double) milliseconds / HOUR));
		}
	}

	private class AboutOneDay extends Renderer {
		public AboutOneDay() {
			super(2 * DAY, Type.BOTH);
		}

		@Override
		public MessageSourceResolvable render(final long milliseconds) {
			return new Message("about.x.days", 1L);
		}
	}

	private class AboutXDays extends Renderer {
		public AboutXDays() {
			super(MONTH, Type.BOTH);
		}

		@Override
		public MessageSourceResolvable render(final long milliseconds) {
			return new Message("about.x.days", Math.round((double) milliseconds / DAY));
		}
	}

	private class AboutOneMonth extends Renderer {
		public AboutOneMonth() {
			super(2 * MONTH, Type.BOTH);
		}

		@Override
		public MessageSourceResolvable render(final long milliseconds) {
			return new Message("about.x.months", 1L);
		}
	}

	private class AboutXMonths extends Renderer {
		public AboutXMonths() {
			super(YEAR, Type.BOTH);
		}

		@Override
		public MessageSourceResolvable render(final long milliseconds) {
			return new Message("about.x.months", Math.round((double) milliseconds / MONTH));
		}
	}

	private class AboutOneYear extends Renderer {
		public AboutOneYear() {
			super(2 * YEAR, Type.BOTH);
		}

		@Override
		public MessageSourceResolvable render(final long milliseconds) {
			return new Message("about.x.years", 1L);
		}
	}

	private class OverXYears extends Renderer {
		public OverXYears() {
			super(-1, Type.BOTH);
		}

		@Override
		public MessageSourceResolvable render(final long milliseconds) {
			return new Message("over.x.years", milliseconds / YEAR);
		}
	}

	private class Message extends DefaultMessageSourceResolvable {
		private static final long serialVersionUID = 1L;

		public Message(final String key, final Object... args) {
			super(new String[] { messagePrefix + key }, args);
		}
	}
}
