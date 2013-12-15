package de.aima13.platform.entity;

import de.aima13.platform.states.Game;
import de.aima13.platform.states.MainMenu;
import de.aima13.platform.util.Face;
import de.aima13.platform.util.Vector;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class Creature extends Entity {
	private final Platform platform;
	private boolean failed = false;

	public Creature(Platform platform) {
		this.platform = platform;
	}

	@Override
	public void onInit() {
		position = new Vector(0, 0);
		velocity = new Vector(5, 5);
		size = new Vector(20, 50);
	}

    @Override
    public void update(int delta)
    {
        if (mayTurn())
        {
            velocity = new Vector(velocity.x * -1, velocity.y);
        }
        Vector newPos = position.add(velocity);
        if (newPos.y + size.y > platform.position.y)
        {
            newPos = new Vector(position.x, newPos.y);
            velocity = new Vector(0, velocity.y);
            failed = true;
        }

		position = newPos;
	}

    public boolean isAbovePlatform()
    {
        if (position.x + size.x >= platform.position.x && position.x < platform.position.x + platform.size.x)
        {
            System.out.println("Above platform!");
            return true;
        }
        return false;
    }

    public boolean isRising()
    {
        return velocity.y < 0;
    }

    public boolean mayTurn()
    {
        if (isAbovePlatform() && isRising())
        {
            return Math.abs(this.platform.position.y - this.position.y) > 100;
        }
        return false;
    }

    @Override
    public void render(Graphics g)
    {
        super.render(g);
        Color c = g.getColor();
        g.setColor(Color.green);
        g.drawRect(position.x, position.y, size.x, size.y);
        g.fillRect(position.x, position.y, size.x, size.y);
        g.setColor(c);
    }

	@Override
	public void onCollide(Entity current, Face collidedFace) {
		if (!failed) {
			this.position = new Vector(this.position.x, current.position.y
					- this.size.y - 1);
			this.velocity = new Vector(this.velocity.x, this.velocity.y * -1);
		}
	}

	@Override
	public void onCollideWithBorder(Face collidedFace) {
		if (failed) {
			die();
		} else {
			float x = position.x;
			float y = position.y;

			float vX = velocity.x;
			float vY = velocity.y;

			switch (collidedFace) {
			case TOP:
				y = 0;
				vY *= -1;
				break;
			case BOTTOM:
				failed = true;
				break;
			case LEFT:
				x = 0;
				vX *= -1;
				break;
			case RIGHT:
				x = level.getWidth() - size.x - 1;
				vX *= -1;
				break;
			}

			position = new Vector(x, y);
			velocity = new Vector(vX, vY);
		}
	}

	@Override
	public void onDeath()  {
		level.getGame().enterState(MainMenu.ID,
				new FadeOutTransition(Color.black),
				new FadeInTransition(Color.black));
		try {
			((Game) level.getGame().getState(Game.ID)).resetState();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
