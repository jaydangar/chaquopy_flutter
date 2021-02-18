def main(code):
    env = {}
    exec(code, env, env)
    
