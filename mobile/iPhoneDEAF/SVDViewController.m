//
//  SVDViewController.m
//  ipadress
//
//  Created by developer on 12. 1. 9..
//  Copyright (c) 2012 HeadFlow All rights reserved.
//

#import "SVDViewController.h"
#import "BIDViewController.h"
@implementation SVDViewController

@synthesize ipAddress;
@synthesize port;
@synthesize connect;


- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

- (void)viewDidLoad
{
    [super viewDidLoad];
    //	 Do any additional setup after loading the view, typically from a nib.
    UIImage *buttonImageNormal = [UIImage imageNamed:@"whiteButton.png"];
    UIImage *stretchableButtonImageNormal = [buttonImageNormal
                                             stretchableImageWithLeftCapWidth:12 topCapHeight:0];
    [connect setBackgroundImage:stretchableButtonImageNormal
                       forState:UIControlStateNormal];
    
    UIImage *buttonImagePressed = [UIImage imageNamed:@"blueButton.png"];
    UIImage *stretchableButtonImagePressed = [buttonImagePressed
                                              stretchableImageWithLeftCapWidth:12 topCapHeight:0];
    [connect setBackgroundImage:stretchableButtonImagePressed
                       forState:UIControlStateHighlighted];
}

- (void)viewDidUnload
{
    [self setIpAddress:nil];
    
    [self setPort:nil];
    //    [self setConnect:nil];
    
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
}

- (void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:animated];
}

- (void)viewWillDisappear:(BOOL)animated
{
	[super viewWillDisappear:animated];
}

- (void)viewDidDisappear:(BOOL)animated
{
	[super viewDidDisappear:animated];
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    
    return NO;
}

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender
{
    if ([[segue identifier] isEqualToString:@"MySegue"]) {
        
        
        BIDViewController *vc = segue.destinationViewController;
        
        vc.ipsend = ipAddress.text;
        vc.portsend = port.text;
        
        
        
    }
}


- (IBAction)textFieldDoneEditing:(id)sender { 
    [sender resignFirstResponder];
}

- (IBAction)backgroundTap:(id)sender { 
    [ipAddress resignFirstResponder];
    [port resignFirstResponder];
}


- (void)dealloc {
    
    [super dealloc];
}
@end
